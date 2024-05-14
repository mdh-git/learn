package com.mdh.python;

import org.python.jline.internal.InputStreamReader;

import java.io.BufferedReader;
import java.io.IOException;

public class CallPython_Demo2 {
    public static void main(String[] args) {
        try {
            // 配置到环境变量
            String args1 = null;
            String args2 = null;
            String args3 = "0";
            String args4 = null;
            String args5 = null;
            String args6 = ".doc";

            //如果是windows执行，不需要加这个
            String osName = System.getProperty("os.name", "");
            if (osName.startsWith("Mac OS")) {

            } else if (osName.startsWith("Windows")) {
                args1 = "C:\\Python\\Python312\\python.exe";
                args2 = "E:\\mdh\\learn\\src\\main\\resources\\python\\oleStream.py";
                args4 = "C:\\Users\\Lenovo\\Desktop\\image\\MICROSOFT_WORD,2071984.946630,3317960.709277,b3f963ee-5ae8-498e-a0fb-18d66fb60232.docx.ole";
                args5 = "C:\\Users\\Lenovo\\Desktop\\image\\60232.docx";
            } else { // assume Unix or Linux

            }


            String[] arguments = new String[] {
                    args1,
                    args2,
                    args3,
                    args4,
                    args5,
                    args6};

            // 执行py文件，注意：这里的命令都最好用绝对路径，到底用哪一个环境的下的Python，比如env下面的tf2的环境；具体执行哪个python文件，也是绝对路径
            Process proc = Runtime.getRuntime().exec(arguments); // 执行py文件，
            // 用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            //输出结果到Java的控制台
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
