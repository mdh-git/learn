package com.mdh.json;

/**
 * json字符串解析
 *
 * String -> JSONObject
 * JSONObject.parseObject(str)
 *
 * JSONObject根据key获取JSONArray
 * JSONArray jsonArray = JSONObject.getJSONArray(key);
 *
 * JSONObject根据key获取JSONObject
 * JSONObject jsonObject = JSONObject.getJSONObject(key);
 *
 * JSONObject get到对应的值,其中"str"为json对象里面的key
 * Object object = JSONObject.get("str");
 *
 * @Author: madonghao
 * @Date: 2018/12/15 13:53
 */
public class JsonTest {

    public static void main(String[] args) {
        String json = "{\n" +
                "  \"is_done\": true,\n" +
                "  \"request\": {\n" +
                "    \"request_id\": \"7kegg3s2xqrm9l63\",\n" +
                "    \"prov_code\": \"410000\",\n" +
                "    \"city_code\": \"410100\",\n" +
                "    \"vehicle_nature\": \"211\",\n" +
                "    \"biz_start_date\": null,\n" +
                "    \"force_start_date\": \"2019-02-13\",\n" +
                "    \"biz_start_time\": null,\n" +
                "    \"force_start_time\": \"2019-02-13 00:00:00\"\n" +
                "  }\n" +
                "}";
    }
}
