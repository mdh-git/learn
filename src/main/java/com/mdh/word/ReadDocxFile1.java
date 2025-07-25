package com.mdh.word;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ReadDocxFile1 {
    public static void main(String[] args) throws Exception {

        readDocx();

    }

    public static void readDocx() throws Exception {
        InputStream is;
        String filePath = "C:\\Users\\Lenovo\\Desktop\\3.docx";
        is = new FileInputStream(filePath);
        XWPFDocument xwpf = new XWPFDocument(is);

        List<IBodyElement> ibs= xwpf.getBodyElements();
        for(IBodyElement ib:ibs)
        {
            BodyElementType bet = ib.getElementType();
            if(bet== BodyElementType.TABLE)
            {
                //表格
                System.out.println("table" + ib.getPart());
            }
            else
            {
                //段落
                XWPFParagraph para = (XWPFParagraph) ib;
                System.out.println("It is a new paragraph....The indention is "
                        + para.getFirstLineIndent() + "," + para.getIndentationFirstLine() );
                //System.out.println(para.getCTP().xmlText());

                List<XWPFRun> res = para.getRuns();
                //System.out.println("run");
                if(res.size()<=0)
                {
                    System.out.println("empty line");
                }
                for(XWPFRun re: res)
                {
                    if(null == re.text()||re.text().length()<=0)
                    {
                        if(re.getEmbeddedPictures().size()>0)
                        {
                            System.out.println("image***" + re.getEmbeddedPictures().size());
                        } else
                        {
                            System.out.println("objects:" + re.getCTR().getObjectList().size());
                            if(re.getCTR().xmlText().indexOf("instrText") > 0) {
                                System.out.println("there is an equation field");
                            }
                            else
                            {
                                //System.out.println(re.getCTR().xmlText());
                            }
                        }
                    }
                    else
                    {
                        System.out.println("==="+ re.getCharacterSpacing() + re.text());
                    }
                }
            }
        }
        is.close();
    }
}
