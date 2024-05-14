package com.mdh.pdf;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class OLEtoDocxConverter {

    public static void main(String[] args) throws Exception {
        String inputFilePath = "E:\\work\\1.xls"; // 输入的OLE文件路径
        String outputFilePath = "E:\\work\\output_docx_file.docx"; // 输出的DOCX文件路径

        FileInputStream fis = new FileInputStream(inputFilePath);
        HSSFWorkbook workbook = new HSSFWorkbook(fis);

        XWPFDocument docx = new XWPFDocument();

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            HSSFSheet sheet = workbook.getSheetAt(i);

            for (Row row : sheet) {
                XWPFParagraph paragraph = docx.createParagraph();

                for (Cell cell : row) {
                    XWPFRun run = paragraph.createRun();

                    CellType cellType = cell.getCellType();
                    if(cellType.equals(CellType.NUMERIC)){
                        run.setText(String.valueOf(cell.getNumericCellValue()));
                    } else if(cellType.equals(CellType.STRING)) {
                        run.setText(cell.getStringCellValue());
                    } else if(cellType.equals(CellType.BLANK)) {
                        run.setText(cell.getStringCellValue());
                    } else {
                        run.addBreak();
                    }
                }
            }
        }

        FileOutputStream fos = new FileOutputStream(outputFilePath);
        docx.write(fos);

        System.out.println("转换完成！");
    }
}
