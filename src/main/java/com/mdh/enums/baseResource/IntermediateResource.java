package com.mdh.enums.baseResource;

import com.mdh.enums.StatusEnums;

/**
 * @author madonghao
 * @create 2020-03-27 10:54
 **/
public class IntermediateResource extends BaseResource {

    public IntermediateResource(Integer code){
        super(code, StatusEnums.INTERMEDIATE.getMsg());
    }
}
