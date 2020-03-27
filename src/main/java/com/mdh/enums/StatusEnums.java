package com.mdh.enums;

/**
 * 枚举
 *
 * @author madonghao
 * @create 2020-03-27 10:24
 **/
public enum StatusEnums {

    SUCCESS(0, "成功"),
    PRIMARY(1, "初级"),
    INTERMEDIATE(2, "中级"),
    ADVANCED(3, "高级"),
    FAIL(9, "失败");

    private Integer code;
    private String msg;

    StatusEnums(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }


    public Integer getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }
}
