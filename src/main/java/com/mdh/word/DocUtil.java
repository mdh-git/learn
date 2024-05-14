package com.mdh.word;

import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DocUtil {

    public static void docxToHtml(String filePath , String htmlPath) throws IOException {
        //获取文件名称
        String myFileName = getFileNameInfo(filePath , 0);

        //图片存放路径
        String imagePath = htmlPath + File.separator + myFileName + getDataTime();
        //转换的html文件路径 与图片在同目录中
        String fileOutName = imagePath + File.separator + myFileName + ".html";

        long startTime = System.currentTimeMillis();
        //获取一个用操作Word的对象
        XWPFDocument document = new XWPFDocument(new FileInputStream(filePath));
        //导出为html时的一些基本设置类
        XHTMLOptions options = null;
        //判断word文件中是否有图片
        if(document.getAllPictures().size() > 0) {
            //获取默认的对象，设置缩进indent
            options = XHTMLOptions.getDefault().indent(4);
            // 如果包含图片的话，要设置图片的导出位置
            File imageFolder = new File(imagePath);
            //设置图片抽取器的目的地文件夹 用于存放图片文件
            options.setExtractor(new FileImageExtractor(imageFolder));
            // URI resolver  word的html中图片的目录路径
            options.URIResolver(new BasicURIResolver(imagePath));
        }

        //获取输出的html文件对象
        File outFile = new File(fileOutName);
        //创建所有的父路径，如果不存在父目录的话
        outFile.getParentFile().mkdirs();
        //创建一个输出流
        OutputStream out = new FileOutputStream(outFile);

        //html转换器
        XHTMLConverter.getInstance().convert(document, out, options);
        System.out.println("转换用时： " + fileOutName + " with " + (System.currentTimeMillis() - startTime) + " ms.");
    }


    public static String getDataTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date =new Date();
        return simpleDateFormat.format(date);
    }


    public static String getFileNameAndExtension(String filePath) {
        String str[] = filePath.split("\\\\");
        return str[str.length - 1];
    }


    public static String getFileNameInfo(String str , int position) {
        String fileNameAndExtension = getFileNameAndExtension(str);
        String str1[] = fileNameAndExtension.split("\\.");
        return str1[position];
    }
}
