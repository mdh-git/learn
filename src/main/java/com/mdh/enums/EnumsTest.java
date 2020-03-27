package com.mdh.enums;

import com.mdh.enums.baseResource.AdvancedResource;
import com.mdh.enums.baseResource.BaseResource;
import com.mdh.enums.baseResource.IntermediateResource;
import com.mdh.enums.baseResource.PrimaryResource;
import org.junit.Test;

/**
 * @author madonghao
 * @create 2020-03-27 10:31
 **/
public class EnumsTest {

    @Test
    public void test(){
        StatusEnums[] values = StatusEnums.values();
        for (StatusEnums val : values) {
            System.out.println(val.getCode());
            System.out.println(val.getMsg());
        }
    }

    @Test
    public void test01(){
        Integer code = 1;
        BaseResource baseResource = null;
        if(code == StatusEnums.PRIMARY.getCode()){
            baseResource = new PrimaryResource(code);
        } else if(code == StatusEnums.INTERMEDIATE.getCode()){
            baseResource = new IntermediateResource(code);
        } else if(code == StatusEnums.ADVANCED.getCode()){
            baseResource = new AdvancedResource(code);
        }
        System.out.println(baseResource.getMsg());
    }
}
