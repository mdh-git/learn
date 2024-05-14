package com.mdh.pdf;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.core.io.Resource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WordDemo {

    public static void main(String[] args) throws IOException {
        WriteWordDataDTO dto = new WriteWordDataDTO();
        dto.setProjectName("123");
        XWPFDocument xwpfDocument = writeWord(dto);
        documentToWord(xwpfDocument);
    }


    public static XWPFDocument writeWord(WriteWordDataDTO writeWordDataDTO) throws IOException {
        // InputStream wordIn = this.getClass().getClassLoader().getResourceAsStream("static/commonWordTemplate.docx");
        InputStream inputStream = Files.newInputStream(Paths.get("static/commonWordTemplate.docx"));
        XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
        XWPFParagraph pa = xwpfDocument.getLastParagraph();
        pa.removeRun(0);

        // 首页
        //homePage(writeWordDataDTO, xwpfDocument);

        // 第一个标题
        //firstTitlePage(writeWordDataDTO, xwpfDocument);

        // 第二个标题，审图报告明细页
        //twoTitlePage(writeWordDataDTO, xwpfDocument);
        inputStream.close();
        return xwpfDocument;
    }

    private static void documentToWord(XWPFDocument xwpfDocument) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//二进制OutputStream
        xwpfDocument.write(baos);
        InputStream in = new ByteArrayInputStream((baos.toByteArray()));

        String wordFileName = "20240117" + ".docx";
    }
}
