package com.mdh.gzip;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPExample {

    public static void compressFile(String sourceFile, String compressedFile) throws IOException {
        FileInputStream inputStream = new FileInputStream(sourceFile);
        FileOutputStream outputStream = new FileOutputStream(compressedFile);
        // 压缩
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) > 0) {
            gzipOutputStream.write(buffer, 0, bytesRead);
        }

        gzipOutputStream.close();
        outputStream.close();
        inputStream.close();
    }

    public static void decompressFile(String compressedFile, String decompressedFile) throws IOException {
        FileInputStream inputStream = new FileInputStream(compressedFile);
        // 解压
        GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
        FileOutputStream outputStream = new FileOutputStream(decompressedFile);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = gzipInputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, bytesRead);
        }

        outputStream.close();
        gzipInputStream.close();
        inputStream.close();
    }


    public static void main(String[] args) {
        try {
            // 压缩文件
            compressFile("E:\\mdh\\file\\input.txt", "E:\\mdh\\file\\compressed.gzip");
            System.out.println("文件压缩成功！");

            // 解压缩文件
            decompressFile("E:\\mdh\\file\\compressed.gzip", "E:\\mdh\\file\\output.txt");
            System.out.println("文件解压成功");
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
