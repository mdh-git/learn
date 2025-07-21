package com.mdh;

import com.alibaba.fastjson.JSON;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import umontreal.ssj.functionfit.BSpline;

/**
 * Created by madonghao on 2018/9/12.
 */
public class Test {

    private static  final  Pattern pattern = Pattern.compile("[0-9]*");
    private static  final  Pattern pattern1 = Pattern.compile("^\\d+(\\.\\d+)?");
    private static  final  Pattern pattern3 = Pattern.compile("[^x00-xff]*");
    public boolean isNumeric(String str){
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            //language=RegExp
            String s = "^\\\\d+()";
            return false;
        }
        return true;
    }



        public static void main(String[] args) {

            demo1();
            //demo2();


        }

    private static void demo1() {

        // 定义数据点
        double[] x = {-1,0,1, 2, 3, 4, 5};
        double[] y = {1,0,1, 4, 9, 16, 25};

        int degree = x.length > 3 ? 3 : 2;

        BSpline approxBSpline = BSpline.createInterpBSpline(x, y, degree);

        double derivative = approxBSpline.derivative(3);
        System.out.println(derivative);

        double i3 = approxBSpline.evaluate(3);
        System.out.println(i3);

        double i4 = approxBSpline.evaluate(4);
        System.out.println(i4);

        double i25 = approxBSpline.evaluate(2.5);
        System.out.println(i25);

        double i20 = approxBSpline.evaluate(2);
        System.out.println(i20);

        double i21 = approxBSpline.evaluate(2.1);
        System.out.println(i21);


        double i22 = approxBSpline.evaluate(2.2);
        System.out.println(i22);

        double[] knots = approxBSpline.getKnots();

        double[] x1 = approxBSpline.getX();
        double[] y1 = approxBSpline.getY();

        System.out.println(JSON.toJSONString(knots));
        System.out.println(JSON.toJSONString(x1));
        System.out.println(JSON.toJSONString(y1));

    }


    private static void demo2() {

        // 定义数据点
        double[] x = {0,1,2,1,0};
        double[] y = {2,3,2,2,0};

        int degree = x.length > 3 ? 3 : 2;

        BSpline approxBSpline = BSpline.createInterpBSpline(x, y, degree);

        double derivative = approxBSpline.derivative(3);
        System.out.println(derivative);

        double i3 = approxBSpline.evaluate(2);
        System.out.println(i3);

        double i4 = approxBSpline.evaluate(1);
        System.out.println(i4);
    }


}
