package com.mdh.algorithm.LeetCode.simple;

import org.springframework.util.StringUtils;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/valid-parentheses/ 20. 有效的括号
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 *
 * 有效字符串需满足：
 * 1.左括号必须用相同类型的右括号闭合。
 * 2.左括号必须以正确的顺序闭合。
 *
 * @Author: madonghao
 * @Date: 2021/3/18 11:16 上午
 */
public class ValidBrackets {

    public static void main(String[] args) {
        //

        String str1 = "()";
        String str2 = "()[]{}";
        String str3 = "(]";
        String str4 = "([)]";
        String str5 = "{[]}";
        String str6 = "){";
        String str7 = "))";
//        System.out.println(isValid(str1));
//        System.out.println(isValid(str2));
//        System.out.println(isValid(str3));
//        System.out.println(isValid(str4));
//        System.out.println(isValid(str5));
//        System.out.println(isValid(str6));
        System.out.println(isValid1(str7));
    }

    public static boolean isValid1(String s) {
        if(StringUtils.isEmpty(s)){
            return false;
        }
        if(s.length() % 2 == 1){
            return false;
        }

        //构建栈
        Stack<Character> stack = new Stack<Character>();
        for (char character: s.toCharArray()) {
            if(character == '('){
                stack.push(character);
                continue;
            }
            if(character == '{'){
                stack.push(character);
                continue;
            }
            if(character == '['){
                stack.push(character);
                continue;
            }
            if(character == ')'){
                if(!stack.isEmpty()){
                    Character peek = stack.peek();
                    if(peek == '('){
                        stack.pop();
                        continue;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            if(character == '}'){
                if(!stack.isEmpty()){
                    Character peek = stack.peek();
                    if(peek == '{'){
                        stack.pop();
                        continue;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            if(character == ']'){
                if(!stack.isEmpty()){
                    Character pop = stack.peek();
                    if(pop == '['){
                        stack.pop();
                        continue;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        if(stack.empty()){
            return true;
        }
        return false;
    }


    public static boolean isValid2(String s) {
        if(StringUtils.isEmpty(s)){
            return false;
        }
        // 当字符串长度为奇数的时候，属于无效情况
        // 条件说明了长度至少为 1，所以不需要在判空
        if (s.length() % 2 == 1) {
            return false;
        }

        //构建栈
        Stack<Character> stack = new Stack<Character>();

        //由外向内遍历字符串
        for(char c : s.toCharArray()){

            if(c == '('){
                stack.push(')');
            }else if(c == '['){
                stack.push(']');
            }else if( c == '{'){
                stack.push('}');
            }else if( stack.isEmpty() || c != stack.pop()){
                //表明有多余的括号
                return false;
            }
        }
        return stack.isEmpty();
    }
}
