package com.mdh.enums.baseResource;

import com.mdh.enums.StatusEnums;

/**
 * @author madonghao
 * @create 2020-03-27 10:51
 **/
public class PrimaryResource extends BaseResource {

    public PrimaryResource(Integer code) {
        super(code ,StatusEnums.PRIMARY.getMsg());

        build();
    }

    private void build() {
        System.out.println("1111");
    }


}
