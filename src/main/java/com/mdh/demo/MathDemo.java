package com.mdh.demo;


import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * Created by madonghao on 2018/10/9.
 */
public class MathDemo {
    public static void main(String[] args) {
        Double a = 1000.40;
        DecimalFormat df=new DecimalFormat("#");
        //System.out.println(df.format(a));

        //System.out.println(Math.pow(4,2)+4);
        //System.out.println(12>>1);

        String str = "We Are Happy";
        String str1 = str.replaceAll(" ","%20");

        StringBuffer sb = new StringBuffer(str);
        //System.out.println(replaceSpace(sb));

        System.out.println(movingCount(10,7,1000));
        System.out.println(movingCount1(10,16,100));

        Integer aInt = null;
    }

    public static String replaceSpace(StringBuffer str) {
        if(str == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for(int i = 0 , len = str.length() ; i < len; i++ ) {
            if(str.charAt(i) == ' ' ){
                sb.append("%20");
            } else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    public static int movingCount(int threshold, int rows, int cols)
    {
        boolean[] visited=new boolean[rows*cols];
        return movingCountCore(threshold, rows, cols, 0,0,visited);
    }
    private static int movingCountCore(int threshold, int rows, int cols,
                                int row,int col,boolean[] visited) {
        if(row<0||row>=rows||col<0||col>=cols) return 0;
        int i=row*cols+col;
        if(visited[i]||!checkSum(threshold,row,col)) return 0;
        visited[i]=true;
        return 1+movingCountCore(threshold, rows, cols,row,col+1,visited)
                +movingCountCore(threshold, rows, cols,row,col-1,visited)
                +movingCountCore(threshold, rows, cols,row+1,col,visited)
                +movingCountCore(threshold, rows, cols,row-1,col,visited);
    }
    private static boolean checkSum(int threshold, int row, int col) {
        int sum=0;
        while(row!=0){
            sum+=row%10;
            row=row/10;
        }
        while(col!=0){
            sum+=col%10;
            col=col/10;
        }
        if(sum>threshold) return false;
        return true;
    }

    private static int number(int num) {
        int sum=0;
        while(num!=0){
            sum+=num%10;
            num=num/10;
        }

        return sum;
    }

    public static int movingCount1(int threshold, int rows, int cols) {
        int count = 0;
        for(int i = 0 ;i < rows ; i++) {
                for(int j = 0;j < cols ; j++ ) {
                    System.out.println("");
                    count++;
                    if(!checkSum(threshold,i,j)){
                        break;
                    }
                }
        }
        return count;
    }
}
