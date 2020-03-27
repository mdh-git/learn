package com.mdh.enums.baseResource;

import com.mdh.enums.StatusEnums;

/**
 * @author madonghao
 * @create 2020-03-27 10:55
 **/
public class AdvancedResource extends BaseResource {

    public AdvancedResource(Integer code){
        super(code, StatusEnums.ADVANCED.getMsg());
    }
}
