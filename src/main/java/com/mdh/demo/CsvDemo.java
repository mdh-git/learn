package com.mdh.demo;

import java.io.*;

/**
 * @Author: madonghao
 * @Date: 2022/3/28 4:57 下午
 */
public class CsvDemo {

  public static void main(String[] args) throws IOException {
	  write("/Users/madonghao/Desktop/excel/users1.txt");
  }


	public static void write(String path) throws IOException {

  	    //将写入转化为流的形式
		BufferedWriter bw = new BufferedWriter(new FileWriter(path));
		//一次写一行
	    for (int i = 500000; i < 1000000; i++) {
		    String ss = i + ",'test" + i + "',123456@.qqcom,'测试地址" + i + "',18,1";
		    bw.write(ss);
		    bw.newLine();  //换行用
	    }
        //关闭流
        bw.close();
     }
}
