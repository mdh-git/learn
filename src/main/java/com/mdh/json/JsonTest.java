package com.mdh.json;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.List;
import java.util.Map;

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

//    public static void main(String[] args) {
//        String json = "{\n" +
//                "  \"is_done\": true,\n" +
//                "  \"request\": {\n" +
//                "    \"request_id\": \"7kegg3s2xqrm9l63\",\n" +
//                "    \"prov_code\": \"410000\",\n" +
//                "    \"city_code\": \"410100\",\n" +
//                "    \"vehicle_nature\": \"211\",\n" +
//                "    \"biz_start_date\": null,\n" +
//                "    \"force_start_date\": \"2019-02-13\",\n" +
//                "    \"biz_start_time\": null,\n" +
//                "    \"force_start_time\": \"2019-02-13 00:00:00\"\n" +
//                "  }\n" +
//                "}";
//
//
//    }


        public static void main(String[] args){
            String json1= "{\n" +
                    "    \"data\": [\n" +
                    "        {\n" +
                    "            \"applicationId\": 200012478,\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"applicationId\": 200012819,\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";

    String json2 =
        "[\n"
            + "        {\n"
            + "            \"applicationId\": 200012478,\n"
            + "        },\n"
            + "        {\n"
            + "            \"applicationId\": 200012819,\n"
            + "        }\n"
            + "    ]";

            ListApp listApp = JSON.parseObject(json1, ListApp.class);

            List<App> data = listApp.getData();
            System.out.println(data);

            List<App> apps = JSON.parseArray(json2, App.class);
            System.out.println(apps);
        }
    }

    @Data
    class ListApp{
        List<App> data;
    }

    @Data
    class App{
        private Integer applicationId;
    }

    class Students{
        private String name;
        private Info info;                //第二层对象


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }

        @Override
        public String toString(){
            return "name: "+name+" "+ "info: "+info.toString();
        }
    }

    class Info {
        private int s2;
        private List<String> newList;
        private Sub sub;                    //第三层对象



        public int getS2() {
            return s2;
        }

        public void setS2(int s2) {
            this.s2 = s2;
        }

        public List<String> getNewList() {
            return newList;
        }

        public void setNewList(List<String> newList) {
            this.newList = newList;
        }

        public Sub getSub() {
            return sub;
        }

        public void setSub(Sub sub) {
            this.sub = sub;
        }

        @Override
        public String toString(){
            return "s2: "+s2+" "+"sub: "+sub+" "+"list: "+newList;
        }
    }

    //继承SL对象
    class Sub extends SL{
        private boolean s3;
        private Map<String,Students> map;          //循环嵌套对象

        public boolean isS3() {
            return s3;
        }

        public void setS3(boolean s3) {
            this.s3 = s3;
        }

        public Map<String, Students> getMap() {
            return map;
        }

        public void setMap(Map<String, Students> map) {
            this.map = map;
        }

        @Override
        public String toString(){
            return "s3: "+s3+" "+"map: "+map+" "+"sl: "+super.toString();
        }
    }

    class SL{
        private double sl;

        public double getSl() {
            return sl;
        }

        public void setSl(double sl) {
            this.sl = sl;
        }

        @Override
        public String toString(){
            return "sl: "+sl;
        }
    }
