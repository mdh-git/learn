package com.mdh.algorithm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DESCRIPTION: TODO
 *
 * @author donghao.ma
 * @date 2019/7/18 19:33
 */
public class Test {
//    public static void main(String[] args) {
//        String str = "((你好)OR(欢迎))and(请问|请问您)";
//        char[] chars = str.toCharArray();
//        Stack stack=new Stack();
//        List<String> list = new ArrayList<>();
//        for(int i = 0; i<str.length(); i++){
//            stack.push(chars[i]);
//            if(chars[i] == ')'){
//                stack.pop();
//                String code = "";
//                String pop = stack.pop().toString();
//                while(pop.charAt(0) != '('){
//                    code = pop + code;
//                    pop = stack.pop().toString();
//                }
//                list.add(code);
//            }
//        }
//        System.out.println(JSON.toJSONString(list));
//    }

//    public static void main(String[] args) {
//
//
//        double num = 123.556;
//
//        // 使用NumberFormat
//        NumberFormat formatter = NumberFormat.getInstance();
//        formatter.setMaximumFractionDigits(0);
//        String formattedNum = formatter.format(num);
//        System.out.println(formattedNum); // 输出：123
//
//        // 使用DecimalFormat
//        DecimalFormat df = new DecimalFormat("0");
//        String formattedNumDF = df.format(num);
//        System.out.println(formattedNumDF); // 输出：123
//
//
//         num = 3.64;
//        int intPart = (int) Math.floor(num);
//        System.out.println(intPart);
//
//
//        String mark = "#";
//        String content = "3#楼一层平面图";
//        String[] split = content.split(mark);
//        String[] split1 = content.split("号");
//        System.out.println("111111111111111111");
//        System.out.println(split.length);
//        System.out.println(split[0]);
//        System.out.println(split1.length);
//        System.out.println(split1[0]);
//
//        System.out.println("-----------------------------------");
//        String input = "abc123def456ghi789";
//        String input1 = "七平面图（二十三#)";
//        String input2 = "平面图（A1#)";
//        String input3 = "平面图（A1#)";
//        String text = "example123abc456";
//
//        String regex = "[\u4e00-\u9fa5]{1,}(?![\u4e00-\u9fa5])";
//        String regex1 = "\\d+$";
//        String regex2 = "[a-zA-Z]+\\d+$";
//
//        Pattern pattern = Pattern.compile(regex1);
//        Matcher matcher = pattern.matcher(input);
//
//        while (matcher.find()) {
//            System.out.println(matcher.group());
//            System.out.println("+++++");
//
//        }
//
//
//        String input22 = "A12345";
//        String patternStr = "\\d+";
//
//        Pattern pattern22 = Pattern.compile(patternStr);
//        Matcher matcher22 = pattern22.matcher(input22);
//
//        if (matcher22.matches()) {
//            System.out.println("字符串仅包含数字");
//        } else {
//            System.out.println("字符串包含非数字字符");
//        }
//
//
//        System.out.println("**********");
//        System.out.println(getPrefix(input));
//    }


    public static void main(String[] args) {

        // 定义标签
        label: {
            System.out.println("标签所在位置前");
            // 模拟goto的行为
            System.out.println("模拟goto");
            // 跳到标签所在位置
            //continue label;

            // 以下代码不会被执行
            System.out.println("这行代码不会执行");
        }

        System.out.println("标签所在位置后");


    }
    private static String getPrefix(String title) {
        String regex = "\\d+$";
        String regex4 = "(?![a-zA-Z]+)\\d+(?!\\d)";
        String regex1 = "[\u4e00-\u9fa5]{1,}(?![\u4e00-\u9fa5])";
        String regex2 = "[a-zA-Z]+\\d+";
        String regex3 = "[a-zA-Z]+\\d+$";

        Pattern pattern = Pattern.compile(regex4);
        //Pattern pattern = Pattern.compile(regex1);
        Matcher matcher = pattern.matcher(title);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }
//        if (matcher.find()) {
//            return matcher.group();
//        }
        return null;
    }
}
