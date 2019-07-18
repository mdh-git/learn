package com.mdh.algorithm;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DESCRIPTION: TODO
 *
 * @author donghao.ma
 * @date 2019/7/18 19:33
 */
public class Test {
    public static void main(String[] args) {
        String str = "((你好)OR(欢迎))and(请问|请问您)";
        char[] chars = str.toCharArray();
        Stack stack=new Stack();
        List<String> list = new ArrayList<>();
        for(int i = 0; i<str.length(); i++){
            stack.push(chars[i]);
            if(chars[i] == ')'){
                stack.pop();
                String code = "";
                String pop = stack.pop().toString();
                while(pop.charAt(0) != '('){
                    code = pop + code;
                    pop = stack.pop().toString();
                }
                list.add(code);
            }
        }
        System.out.println(JSON.toJSONString(list));
    }
}
