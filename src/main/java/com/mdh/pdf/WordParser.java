package com.mdh.pdf;

import org.apache.poi.hwpf.*; // for .doc files
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class WordParser {

    public static void main(String[] args) throws Exception {
        String filePath = "E:\\work\\1.docx";

        FileInputStream fis = new FileInputStream(new File(filePath));
        HWPFDocument doc = new HWPFDocument(fis);

        Range range = doc.getRange();
        int numParagraphs = range.numParagraphs();

        for (int i = 0; i < numParagraphs; i++) {
            Paragraph paragraph = range.getParagraph(i);

            System.out.println("段落" + (i+1) + ":");
            System.out.println(paragraph.text());
            System.out.println("\n--------------------\n");
        }

        doc.close();
        fis.close();
    }
}
