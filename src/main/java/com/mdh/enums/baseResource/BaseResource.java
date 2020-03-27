package com.mdh.enums.baseResource;

import com.mdh.enums.Resource;
import lombok.Getter;
import lombok.Setter;

/**
 * @author madonghao
 * @create 2020-03-27 10:47
 **/
public class BaseResource implements Resource {

    @Getter
    @Setter
    private Integer code;

    @Getter
    @Setter
    private String msg;

    protected BaseResource(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getResourceCode() {
        return code;
    }

    @Override
    public String getResourceMsg() {
        return msg;
    }
}
