package com.mdh.python;

import java.io.IOException;
import java.util.Arrays;

public class Demo {
    public static void main(String[] args) {
        try {
            // 创建 ProcessBuilder 对象
            ProcessBuilder pb = new ProcessBuilder("python", "E:\\mdh\\learn\\src\\main\\resources\\python\\oleStream.py");

            // 设置命令行参数
            pb.command().addAll(Arrays.asList("0", "E:\\work\\result\\1.docx.ole", "E:\\work\\result\\333.docx", ".doc"));

            // 启动进程
            Process process = pb.start();

            // 等待进程结束
            int exitCode = process.waitFor();

            System.out.println("Exit Code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
