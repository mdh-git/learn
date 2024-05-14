package com.mdh.pdf;


import org.apache.poi.hwpf.*; // for .doc files
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileInputStream;
import java.util.List;

public class OLEParser {

    public static void main(String[] args) throws Exception {
        String filePath = "E:\\work\\1.docx";

        if (filePath.endsWith(".doc")) {
            HWPFDocument doc = new HWPFDocument(new FileInputStream(filePath));

            Range range = doc.getRange();
            int numParagraphs = range.numParagraphs();

            System.out.println("Number of paragraphs in the document: " + numParagraphs);

            for (int i = 0; i < numParagraphs; i++) {
                Paragraph para = range.getParagraph(i);

                System.out.print("Paragraph " + (i+1) + ": ");
                System.out.println(para.text());
            }

            doc.close();
        } else if (filePath.endsWith(".docx")) {
            XWPFDocument doc = new XWPFDocument(new FileInputStream(filePath));

            List<XWPFParagraph> paragraphs = doc.getParagraphs();

            System.out.println("Number of paragraphs in the document: " + paragraphs.size());

            for (int i = 0; i < paragraphs.size(); i++) {
                XWPFParagraph para = paragraphs.get(i);

                System.out.print("Paragraph " + (i+1) + ": ");
                System.out.println(para.getText());
            }

            doc.close();
        } else {
            throw new IllegalArgumentException("Unsupported file format");
        }
    }

}
