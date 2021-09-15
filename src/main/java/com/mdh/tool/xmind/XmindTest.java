package com.mdh.tool.xmind;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import com.google.common.collect.Lists;
import org.xmind.core.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: madonghao
 * @Date: 2021/9/13 9:31 上午
 */
public class XmindTest {

    /**
     * 当前类路径
     */
    public static final String CLASS_PATH = XmindTest.class.getResource("/").getPath();
    /**
     * 文件分隔符
     */
    public static final String FILE_SEPARATOR = SystemUtil.getOsInfo().getFileSeparator();

    public static void main(String[] args) throws IOException, CoreException {
        // 读取目录
        String bookName = "本方藥對";
        List<String> contents = FileUtil.readLines(CLASS_PATH + bookName + ".txt", "utf-8");

        // 创建思维导图的工作空间
        IWorkbookBuilder workbookBuilder = Core.getWorkbookBuilder();
        IWorkbook workbook = workbookBuilder.createWorkbook();

        // 获得默认sheet
        ISheet primarySheet = workbook.getPrimarySheet();

        // 获得根主题
        ITopic rootTopic = primarySheet.getRootTopic();
        // 设置根主题的标题
        rootTopic.setTitleText(bookName);

        // 章节 topic 的列表
        ArrayList<ITopic> chapterTopics = Lists.newArrayList();
        String lastTopicId = null;
        HashMap<String, ITopic> map = new HashMap<>();

        for (String content : contents) {
            if (StrUtil.isNotBlank(content)) {
                ITopic topic = workbook.createTopic();
                topic.setTitleText(content);
                String index = findIndex(content.toCharArray());
                topic.setStyleId(index);
                if (ReUtil.isMatch("\\S+", content)) {
                    // 创建章节节点
                    chapterTopics.add(topic);
                    lastTopicId = topic.getId();
                } else {
                    // 创建小节节点

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
            }
        }

        // 把章节节点添加到要节点上
        chapterTopics.forEach(it -> rootTopic.add(it, ITopic.ATTACHED));

        // 保存
        workbook.save(CLASS_PATH + FILE_SEPARATOR + bookName + ".xmind");

    }


    public static String findIndex(char[] chars) {
        for (int i = 0; i < chars.length; i++) {
            if (!CharUtil.isBlankChar(chars[i])) {
                return "" + i;
            }
        }
        return "-1";
    }
}
