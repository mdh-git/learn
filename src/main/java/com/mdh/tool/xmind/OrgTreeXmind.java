package com.mdh.tool.xmind;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.xmind.core.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: madonghao
 * @Date: 2021/9/13 10:53 上午
 */
public class OrgTreeXmind {

    static IWorkbook workbook = null;

    public static void main(String[] args) throws IOException, CoreException {
        OrgTree orgTree = getData();
        System.out.println(JSON.toJSONString(orgTree));
        String label = orgTree.getLabel();
        // 创建思维导图的工作空间
        IWorkbookBuilder workbookBuilder = Core.getWorkbookBuilder();
        workbook = workbookBuilder.createWorkbook();

        // 获得默认sheet
        ISheet primarySheet = workbook.getPrimarySheet();

        // 获得根主题
        ITopic rootTopic = primarySheet.getRootTopic();
    // 设置根主题的标题
        rootTopic.setTitleText(label + "\n马东豪\nP3\n在职员工");
        rootTopic.setTitleWidth(2);
        rootTopic.setStructureClass("org.xmind.ui.org-chart.down");
        rootTopic.setFolded(true);

        IPlainNotesContent plainContent = (IPlainNotesContent) workbook.createNotesContent(INotes.PLAIN);
        plainContent.setTextContent("跟节点达达集团");
        INotes notes = rootTopic.getNotes();
        notes.setContent(INotes.PLAIN, plainContent);


        // 章节 topic 的列表
        ArrayList<ITopic> chapterTopics = Lists.newArrayList();
        String lastTopicId = null;
        HashMap<String, ITopic> map = new HashMap<>();

        int index = 0;
        buildITopic(orgTree, index, chapterTopics, lastTopicId, map);

        // 把章节节点添加到要节点上
        chapterTopics.forEach(it -> rootTopic.add(it, ITopic.ATTACHED));

        // 保存
        workbook.save( "/Users/madonghao/xmind/" + label + ".xmind");
    }

    private static void buildITopic(OrgTree orgTree, int index, ArrayList<ITopic> chapterTopics, String lastTopicId, HashMap<String, ITopic> map) {
        List<OrgTree> childrenOrg = orgTree.getChildren();
        if(CollectionUtils.isNotEmpty(childrenOrg)){
            for (OrgTree org : childrenOrg) {
                ITopic topic = workbook.createTopic();
                topic.setTitleText(org.getLabel());
                topic.setStyleId(String.valueOf(index));
                topic.setFolded(true);

                if(index == 0){
                    // 创建章节节点
                    chapterTopics.add(topic);
                    lastTopicId = topic.getId();
                } else {
                    ITopic lastTopic = map.get(lastTopicId);

                    if (new Integer(topic.getStyleId()) > new Integer(lastTopic.getStyleId())) {
                        lastTopic.add(topic, ITopic.ATTACHED);
                    }

                    if (new Integer(topic.getStyleId()).equals(new Integer(lastTopic.getStyleId()))) {
                        ITopic parent = lastTopic.getParent();
                        parent.add(topic, ITopic.ATTACHED);
                    }

                    if (new Integer(topic.getStyleId()) < new Integer(lastTopic.getStyleId())) {
                        ITopic parent = lastTopic.getParent();
                        while (!new Integer(topic.getStyleId()).equals(new Integer(parent.getStyleId()))) {
                            parent = parent.getParent();
                        }
                        ITopic realParent = parent.getParent();
                        realParent.add(topic, ITopic.ATTACHED);
                    }
                    lastTopicId = topic.getId();
                }

                map.put(topic.getId(), topic);

                List<OrgTree> orgChildren = org.getChildren();
                if(CollectionUtils.isNotEmpty(orgChildren)){
                    buildITopic(org, ++index, chapterTopics, lastTopicId, map);
                    --index;
                }
            }
        }
    }

    private static OrgTree getData() {
        OrgTree orgTree = new OrgTree();
        orgTree.setLabel("达达集团");

        OrgTree orgTree11 = new OrgTree();
        orgTree11.setLabel("CTO体系");

        OrgTree orgTree1121 = new OrgTree();
        orgTree1121.setLabel("物流产品研发部");
        orgTree1121.setName("name1name1");
        orgTree1121.setJob("p3");
        OrgTree orgTree1122 = new OrgTree();
        orgTree1122.setLabel("到家产品研发部");
        orgTree1122.setName("name2name2");
        orgTree1122.setJob("p3");
        OrgTree orgTree1123 = new OrgTree();
        orgTree1123.setLabel("大数据和算法应用部");
        orgTree1123.setName("name3name3");
        orgTree1123.setJob("P4");
        OrgTree orgTree1124 = new OrgTree();
        orgTree1124.setLabel("效率中台产品研发部");
        orgTree1124.setName("name4name4");
        orgTree1124.setJob("P5");
        OrgTree orgTree1125 = new OrgTree();
        orgTree1125.setLabel("云平台部");
        orgTree1125.setName("name5name3");
        orgTree1125.setJob("P6");
        List<OrgTree> children11 = Lists.newArrayList();
        children11.add(orgTree1121);
        children11.add(orgTree1122);
        children11.add(orgTree1123);
        children11.add(orgTree1124);
        children11.add(orgTree1125);
        orgTree11.setChildren(children11);

        List<OrgTree> children1123 = Lists.newArrayList();
        OrgTree orgTree11231 = new OrgTree();
        orgTree11231.setLabel("算法应用部");
        OrgTree orgTree11232 = new OrgTree();
        orgTree11232.setLabel("数据分析部");
        OrgTree orgTree11233 = new OrgTree();
        orgTree11233.setLabel("数据产品部");
        children1123.add(orgTree11231);
        children1123.add(orgTree11232);
        children1123.add(orgTree11233);
        orgTree1123.setChildren(children1123);

        OrgTree orgTree12 = new OrgTree();
        orgTree12.setLabel("CFO体系");
        OrgTree orgTree13 = new OrgTree();
        orgTree13.setLabel("职能体系");
        OrgTree orgTree14 = new OrgTree();
        orgTree14.setLabel("达达业务部");
        OrgTree orgTree15 = new OrgTree();
        orgTree15.setLabel("到家业务部");
        OrgTree orgTree16 = new OrgTree();
        orgTree16.setLabel("总裁办");

        List<OrgTree> children1 = Arrays.asList(orgTree11, orgTree12, orgTree13, orgTree14, orgTree15, orgTree16);
        orgTree.setChildren(children1);

        return orgTree;
    }


}
