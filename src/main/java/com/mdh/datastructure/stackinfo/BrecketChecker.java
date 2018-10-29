package com.mdh.datastructure.stackinfo;

/**
 *
 * CharStack中用于封装分隔符匹配的操作
 * @author madonghao
 * @date 2018/10/29
 */
public class BrecketChecker {

    /** 存储待检查的字符串 */
    private String input;

    /** 构造方法，接受待检查的字符串 */
    public BrecketChecker(String in){
        this.input = in;
    }

    /** 检查分隔符匹配的方法 */
    public void check(){
        int strLength = input.length();
        CharStack stack = new CharStack(strLength);

        for(int i=0;i<strLength;i++){

            //一次获取串中的单个字符
            char chx =input.charAt(i);

            switch(chx){
                case '{' :
                case '[' :
                case '(' :
                    //如果为左分隔符，压入栈
                    stack.push(chx);
                    break;
                case '}' :
                case ']' :
                case ')' :
                    //如果为右分隔符，与栈顶元素进行匹配
                    if(!stack.isEmpty()){
                        char ch = stack.pop();

                        if((ch== '{' && chx != '}')||
                                (ch == '(' && chx != ')')||
                                (ch == '[' && chx != ']')
                                ){
                            System.out.println("匹配出错！字符："+ch+",下标："+i);
                        }
                    }else{
                        System.out.println("匹配出错！字符："+chx+",下标："+i);
                    }

                default :
                    break;
            }

        }

        if(!stack.isEmpty()){
            //匹配结束时如果栈中还有元素，证明右分隔符缺失
            System.out.println("有括号没有关闭！");
        }
    }

}
