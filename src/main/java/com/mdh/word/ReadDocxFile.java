package com.mdh.word;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class ReadDocxFile {
    public static void main(String[] args) throws IOException {
        // 指定要读取的.docx文件路径
        String filePath = "E:\\work\\3.docx";

        // 创建XWPFDocument对象并加载.docx文件
        XWPFDocument document = new XWPFDocument(new FileInputStream(filePath));

        List<IBodyElement> bodyElements = document.getBodyElements();
        for (IBodyElement element : bodyElements) {
            if (element instanceof XWPFParagraph) {
                XWPFParagraph paragraph = (XWPFParagraph) element;
                String text = paragraph.getText();
                if (text != null && !text.isEmpty()) {
                    //处理段落或正文
                } else {
                    // 顺序遍历图片
                    paragraph.getIRuns().forEach(run -> {
                        if (run instanceof XWPFRun) {
                            XWPFRun xWPFRun = (XWPFRun) run;
                            for (XWPFPicture picture : xWPFRun.getEmbeddedPictures()) {
                                XWPFPictureData pictureData = picture.getPictureData();
                                String base64Image = "<img src='data:image/png;base64," + Base64.getEncoder().encodeToString((pictureData.getData())) + "'/>";
                            }
                        }
                    });
                }
            } else if (element instanceof XWPFTable) {
                //处理表格
                XWPFTable table = (XWPFTable) element;
                String text = table.getText();
                System.out.println(text);
            }
        }

        // 获取所有段落
//        for (XWPFParagraph paragraph : document.getParagraphs()) {
//            System.out.println("段落内容：" + paragraph.getText());
//
//            // 获取段落中的每个run（包含样式信息）
//            List<XWPFRun> runs = paragraph.getRuns();
//            if (!runs.isEmpty()) {
//                for (XWPFRun run : runs) {
//
//                    if (run.isEmbeddedPicturesPresent() && !run.isEmpty()) {
//                        byte[] imageBytes = IOUtils.toByteArray(run.getPictureData().getInputStream());
//                        // 在此处理获得到的图像数据（imageBytes）
//                        // ...
//                    } else {
//                        System.out.print(run.text());
//                    }
//
//                }
//            } else {
//                System.out.println("该段落没有任何运行！");
//            }
//        }

        // 关闭document流
        document.close();
    }



}
