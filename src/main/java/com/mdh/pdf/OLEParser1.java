package com.mdh.pdf;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class OLEParser1 {
    public static void main(String[] args) throws IOException {
        String filePath = "E:\\work\\result\\222.docx";
//        XWPFDocument document = new XWPFDocument(new FileInputStream(filePath));
//        XWPFWordExtractor extractor = new XWPFWordExtractor(document);
//        String text = extractor.getText();



        XWPFDocument doc = new XWPFDocument(new FileInputStream(filePath));
        List<XWPFParagraph> paragraphs = doc.getParagraphs();
        for (XWPFParagraph para : paragraphs) {
            String text = para.getText();
            System.out.println("段落内容：" + text);
        }

        for (XWPFParagraph para : paragraphs) {
            List<XWPFRun> runs = para.getRuns();
            for (XWPFRun run : runs) {
                List<XWPFPicture> embeddedPictures = run.getEmbeddedPictures();
                for (int i = 0; i < embeddedPictures.size(); i++) {
                    XWPFPicture xwpfPicture = embeddedPictures.get(i);
                    XWPFPictureData pictureData = xwpfPicture.getPictureData();
                    byte[] data = pictureData.getData();

                }
            }
        }
    }
}
