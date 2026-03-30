package com.mdh.project.showfield;

public interface BaseTypeEnum {
    /**
     * 得到code
     *
     * @return
     */
    default String getCode() {
        return this.toString();
    }

    /**
     * 得到name
     *
     * @return
     */
    String getName();

    /**
     * 通过名称得到对应枚举类型
     *
     * @param name
     * @param c
     * @param <M>
     * @return
     */
    static <M extends BaseTypeEnum> M fromName(String name, Class<M> c) {
        for (M e : c.getEnumConstants()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 通过coe得到对应枚举类型
     *
     * @param code
     * @param c
     * @param <M>
     * @return
     */
    static <M extends BaseTypeEnum> M fromCode(String code, Class<M> c) {
        for (M e : c.getEnumConstants()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 通过coe得到对应枚举类型
     *
     * @param code
     * @param c
     * @return
     */
    static <M extends BaseTypeEnum> String getNameByCode(String code, Class<M> c) {
        for (BaseTypeEnum e : c.getEnumConstants()) {
            if (e.getCode().equals(code)) {
                return e.getName();
            }
        }
        return null;
    }
}
