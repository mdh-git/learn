package com.mdh.project.showfield;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ShowVarHandler {

    public static List<VarTreeDTO> getShowVar(Object obj, String label, String labelField) {
        List<VarTreeDTO> res = new ArrayList<>();
        Deque<VarNode> deque = new ArrayDeque<>();
        addDeque(obj, deque, label, labelField, null);
        JSONConfig jsonConfig = new JSONConfig();
        jsonConfig.setIgnoreNullValue(false);
        while (!deque.isEmpty()) {
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                VarNode node = deque.pop();
                try {
                    VarTreeDTO pVarNode = node.pVarNode;
                    ConvertBase convertBase = node.convertBase;

                    List<Loc> locList = convertBase.getLocList();
                    for (Loc loc : locList) {
                        VarTreeDTO varTreeDTO = new VarTreeDTO();
                        JSONObject attribute = new JSONObject(jsonConfig);
                        attribute.set("objId", convertBase.getObjId());
                        varTreeDTO.setLabel(getLabel(convertBase, node.label, node.labelField));
                        if ((pVarNode != null && !pVarNode.getFrameId().equals(loc.getFrameId()))) {
                            varTreeDTO.setFrameId(pVarNode.getFrameId());
                        } else {
                            varTreeDTO.setItemName(loc.getItemName());
                            varTreeDTO.setFrameId(loc.getFrameId());
                            varTreeDTO.setCoordinateType(loc.getCoordinateTypeEnum());
                            varTreeDTO.setCoordinate(loc.getLocJson());
                        }
                        Field[] fields = convertBase.getClass().getDeclaredFields();
                        for (Field field : fields) {
                            if (field.getName().equals("connectedRooms")) continue;
                            field.setAccessible(true);
                            ShowField showField = field.getAnnotation(ShowField.class);
                            if (showField != null) {

                                if (showField.coordinateType() != CoordinateTypeEnum.NONE) {
                                    addCoordinateObj(loc.getFrameId(), field.get(convertBase), showField, varTreeDTO);
                                } else if (showField.child() || StrUtil.isNotBlank(showField.labelField())) {
                                    addDeque(field.get(convertBase), deque, showField.label(), showField.labelField(), varTreeDTO);
                                } else {
                                    Object val = getAttributeVal(convertBase, field);
                                    if (val instanceof Double && Double.POSITIVE_INFINITY == ((Double) val)) {
                                        // log.warn("{}-{}", showField.label(), val);
                                        continue;
                                    }
                                    if (val != null && showField.isMap()) {
                                        try {
                                            JSONObject jsonObject = JSONUtil.parseObj(val);
                                            jsonObject.forEach((k, v) -> {
                                                attribute.set(k.toString(), v);
                                            });
                                        } catch (Exception e) {
                                            // log.error("{}-{}，Map类型数据转换失败，{}", showField.label(), val, e.getMessage(), e);

                                        }
                                    } else {
                                        attribute.set(showField.label(), val);
                                    }

                                }
                            }
                        }
                        varTreeDTO.setAttribute(attribute.toString());
                        if (node.pVarNode != null) node.pVarNode.getChildren().add(varTreeDTO);
                        else res.add(varTreeDTO);
                    }
                } catch (Throwable e) {
                    // log.error("获取变量展示数据失败，label：{}, labelField：{},{}", node.label, node.labelField, e.getMessage(), e);
                }
            }
        }
        return res;
    }


    public static void addDeque(Object obj, Deque<VarNode> deque, String label, String labelField, VarTreeDTO pNode) {
        if (obj != null) {
            if (obj instanceof Collection) {
                Collection<?> list = (Collection<?>) obj;
                for (Object o : list) {
                    if (o instanceof ConvertBase) {
                        deque.add(new VarNode((ConvertBase) o, label, labelField, pNode));
                    }
                }
            } else {
                if (obj instanceof ConvertBase) {
                    deque.add(new VarNode((ConvertBase) obj, label, labelField, pNode));
                }
            }
        }
    }


    private static String getLabel(ConvertBase convertBase, String label, String labelField) {
        StringBuilder builder = new StringBuilder();
        if (label != null) {
            builder.append(label);
        }
        if (StrUtil.isNotBlank(labelField)) {
            String[] split = labelField.split(",");
            for (String sp : split) {
                Object labelFieldVal = ReviewUtil.getField(convertBase, sp.replace(" ", ""));
                if (labelFieldVal != null) {
                    if (labelFieldVal instanceof Collection && CollUtil.isNotEmpty((Collection<?>) labelFieldVal) && ((Collection<?>) labelFieldVal).toArray()[0] instanceof BaseTypeEnum) {

                        Set<String> lableSet = new HashSet<String>((Collection<? extends String>) labelFieldVal);
                        if (StrUtil.isNotBlank(builder.toString())) builder.append("-");
                        builder.append(StrUtil.join("、", lableSet));
                    } else if (labelFieldVal instanceof BaseTypeEnum) {
                        builder.append(((BaseTypeEnum) labelFieldVal).getName());
                    } else {
                        if (StrUtil.isNotBlank(builder.toString())) builder.append("-");
                        builder.append(labelFieldVal);
                    }
                }
            }
        }

        return builder.toString();
    }

    private static Object getAttributeVal(ConvertBase convertBase, Field field) throws IllegalAccessException {
        Object val = field.get(convertBase);
        if (val == null || (val instanceof Map && ((Map) val).isEmpty()) || (val instanceof Collection && (CollUtil.isEmpty((Collection<?>) val))) || (val instanceof String && StrUtil.isBlank(val.toString())))
            return null;

        if (val instanceof ConvertBase) {
            return ((ConvertBase) val).getObjId();
        } else if (val instanceof Collection && ((Collection<?>) val).toArray()[0] instanceof ConvertBase) {
            return ((Collection<?>) val).stream().map(item -> ((ConvertBase) item).getObjId()).collect(Collectors.toList());
        } else if (isBaseDataType(val)) {
            return val;
        } else if (val instanceof BaseTypeEnum) {
            BaseTypeEnum baseTypeEnumVal = (BaseTypeEnum) val;
            return baseTypeEnumVal.getName();
        } else if (val instanceof Collection && CollUtil.isNotEmpty((Collection<?>) val) && ((Collection<?>) val).toArray()[0] instanceof BaseTypeEnum) {
            Set<String> lableSet = ((Collection<?>) val).stream().map(item -> {
                BaseTypeEnum baseTypeEnumVal = (BaseTypeEnum) item;
                return baseTypeEnumVal.getName();
            }).collect(Collectors.toSet());
            return StrUtil.join("、", lableSet);
        } else {
            return JSONUtil.toJsonStr(val, new JSONConfig().setIgnoreNullValue(false));
        }
    }

    public static boolean isBaseDataType(Object obj) {
        if (obj instanceof Integer || obj instanceof Boolean || obj instanceof Character || obj instanceof Byte || obj instanceof Short || obj instanceof Long || obj instanceof Float || obj instanceof Double || obj instanceof String || obj instanceof JSONObject || obj instanceof JSONArray)
            return true;
        return false;
    }

    private static void addCoordinateObj(String frameId, Object val, ShowField showField, VarTreeDTO varTreeDTO) {
        if (val != null) {
            switch (showField.coordinateType()) {
                case DIAGONAL:
                    if (val instanceof WcsLoc) {
                        addVarTreeChildren(frameId, showField, varTreeDTO, JSONUtil.toJsonStr(((WcsLoc) val).toDiagonal()));
                    } else if (val instanceof Collection && !((Collection<?>) val).isEmpty() && ((Collection<?>) val).toArray()[0] instanceof WcsLoc) {
                        for (Object object : (Collection<?>) val) {
                            addVarTreeChildren(frameId, showField, varTreeDTO, JSONUtil.toJsonStr(((WcsLoc) object).toDiagonal()));
                        }
                    } else if (val instanceof Collection && !((Collection<?>) val).isEmpty() && ((Collection<?>) val).toArray()[0] instanceof Point) {
                        addVarTreeChildren(frameId, showField, varTreeDTO, JSONUtil.toJsonStr(val));
                    }
                    break;
                case RANGE:
                    String coordinate = null;
                    if (val instanceof PolyWCS) {
                        coordinate = JSONUtil.toJsonStr(new Polys(CollUtil.newArrayList((PolyWCS) val)));
                    } else if (val instanceof Collection && !((Collection<?>) val).isEmpty()) {
                        if (((Collection<?>) val).toArray()[0] instanceof PolyWCS) {
                            coordinate = JSONUtil.toJsonStr(new Polys((CollUtil.newArrayList((Collection<PolyWCS>) val))));
                        } else if (((Collection<?>) val).toArray()[0] instanceof Point) {
                            coordinate = JSONUtil.toJsonStr(new Polys(CollUtil.newArrayList(new PolyWCS(CollUtil.newArrayList((Collection<Point>) val)))));
                        }
                    }
                    addVarTreeChildren(frameId, showField, varTreeDTO, coordinate);
                    break;
                case LINE:
                    if (val instanceof LineSegWCS) {
                        addVarTreeChildren(frameId, showField, varTreeDTO, JSONUtil.toJsonStr(CollUtil.newArrayList(val)));
                    } else if (val instanceof Collection && !((Collection<?>) val).isEmpty() && ((Collection<?>) val).toArray()[0] instanceof LineSegWCS) {
                        addVarTreeChildren(frameId, showField, varTreeDTO, JSONUtil.toJsonStr(val));
                    }
                    break;
                case DIAGONAL_ANGLE:
                    break;
            }

        }
    }

    private static void addVarTreeChildren(String frameId, ShowField showField, VarTreeDTO varTreeDTO, String coordinate) {
        VarTreeDTO tesVarTreeDTO = new VarTreeDTO();
        tesVarTreeDTO.setLabel(showField.label());
        tesVarTreeDTO.setFrameId(frameId);
        tesVarTreeDTO.setCoordinateType(showField.coordinateType());
        if (coordinate != null) {
            tesVarTreeDTO.setCoordinate(coordinate);
            varTreeDTO.getChildren().add(tesVarTreeDTO);
        }
    }


    private static class VarNode {
        private ConvertBase convertBase;
        private String label;
        private String labelField;
        private VarTreeDTO pVarNode;

        private VarNode(ConvertBase convertBase, String label, String labelField, VarTreeDTO pVarNode) {
            this.convertBase = convertBase;
            this.label = label;
            this.labelField = labelField;
            this.pVarNode = pVarNode;
        }
    }
}
