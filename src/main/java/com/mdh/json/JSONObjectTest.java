package com.mdh.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @Author donghao.ma
 * @create 2019/4/22 9:03
 */
public class JSONObjectTest {

    public static void main(String[] args) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for(int i = 0; i < 5; i++){
            jsonArray.add(jsonObject.put("id", i));
        }
        System.out.println(JSON.toJSON(jsonArray));
    }
}
