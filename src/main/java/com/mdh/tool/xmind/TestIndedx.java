package com.mdh.tool.xmind;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.system.SystemUtil;
import com.google.common.collect.Lists;
import org.xmind.core.CoreException;
import org.xmind.core.ITopic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * @Author: madonghao
 * @Date: 2021/9/13 9:41 上午
 */
public class TestIndedx {

    /**
     * 当前类路径
     */
    public static final String CLASS_PATH = GeneratorDoubanXmind.class.getResource("/").getPath();
    /**
     * 文件分隔符
     */
    public static final String FILE_SEPARATOR = SystemUtil.getOsInfo().getFileSeparator();

    public static void main2(String[] args) throws IOException, CoreException {
        // 读取目录
        String bookName = "mindMap_Base_01";
        List<String> contents = FileUtil.readLines(CLASS_PATH + bookName + ".txt", "utf-8");



        // 章节 topic 的列表
        ArrayList<ITopic> chapterTopics = Lists.newArrayList();
        for (String content : contents) {
            System.out.println(content);
            // 如果是数字开头为章节名称
            if (ReUtil.isMatch(".+", content)) {
                char[] chars = content.toCharArray();
                int index = findIndex(chars);
                System.out.println(index);
            }
        }

    }


    public static int findIndex(char[] chars) {
        for (int i = 0; i < chars.length; i++) {
            if (!CharUtil.isBlankChar(chars[i])) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String s =  "桂枝+芍藥";
        System.out.println(ReUtil.isMatch("\\S+", s));
    }
}
