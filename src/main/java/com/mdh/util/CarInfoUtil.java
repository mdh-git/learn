package com.mdh.util;
import java.util.Date;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author madonghao
 * @date 2018/10/16
 */
public class CarInfoUtil {

    public static void main(String[] args) {



        CarInfo carInfo1 = new CarInfo("AZHZG06Y1418F037428K","", new Byte("0"),"QZHZG06Y1418F047531E","LS103095",1);
        CarInfo carInfo2 = new CarInfo("AZHZG06Y1418F037428K","", new Byte("0"),"QZHZG06Y1418F047531E","LS103095",2);
        CarInfo carInfo3 = new CarInfo("AZHZG06Y1418F031404A","", new Byte("0"),"QZHZG06Y1418F041849R","豫J0Q526",1);
        CarInfo carInfo4 = new CarInfo("AZHZG06Y1418F031403W","", new Byte("0"),"QZHZG06Y1418F041848Z","豫J0Q526",1);
        CarInfo carInfo5 = new CarInfo("AZHZG06Y1418F031402S","", new Byte("0"),"QZHZG06Y1418F041847V","豫J0Q526",2);

        //交强最全的
        CarInfo carInfo6 = new CarInfo("AZHZG06CTP18F031201S","", new Byte("0"),"QZHZG06Y1418F041229K","豫DPT692",1);
        CarInfo carInfo7 = new CarInfo("AZHZG06Y1418F031202F","", new Byte("0"),"QZHZG06Y1418F041229K","豫DPT692",2);
        CarInfo carInfo8 = new CarInfo("AZHZG06Y1418F031226J","", new Byte("0"),"QZHZG06Y1418F041291E","豫DPT692",1);
        CarInfo carInfo9 = new CarInfo("AZHZG06CTP18F031226I","", new Byte("0"),"QZHZG06Y1418F041291E","豫DPT692",2);

        CarInfo carInfo10 = new CarInfo("AZHZG06Y1418F027744M","AZHZZ31Y1418B044438K", new Byte("1"),"QZHZG06Y1418F035995D","豫P576L2",1);
        CarInfo carInfo11 = new CarInfo("AZHZZ31CTP18B059476Q","AZHZG06CTP18F027762O", new Byte("1"),"QZHZG06Y1418F035995","豫P576L2",2);
        CarInfo carInfo12 = new CarInfo("AZHZG06Y1418F024010L","", new Byte("0"),"QZHZG06Y1418F031012W","豫P576L2",2);
        CarInfo carInfo13 = new CarInfo("AZHZG06CTP18F024010I","", new Byte("0"),"QZHZG06Y1418F031012W","豫P576L2",2);

        CarInfo carInfo14 = new CarInfo("AZHZG06Y1418F0277446","AZHZZ31Y1418B044438f", new Byte("1"),"QZHZG06Y1418F035995J","豫P576L3",1);
        List<CarInfo> list = new ArrayList<>();
        list.add(carInfo1);
        list.add(carInfo2);
        list.add(carInfo3);
        list.add(carInfo4);
        list.add(carInfo5);
        list.add(carInfo6);
        list.add(carInfo7);
        list.add(carInfo8);
        list.add(carInfo9);
        list.add(carInfo10);
        list.add(carInfo11);
        list.add(carInfo12);
        list.add(carInfo13);
        list.add(carInfo14);
        System.out.println(list.size());
        //List<String> collect = list.stream().map(CarInfo::getCarNumber).distinct().collect(Collectors.toList());
        Set<String> noSet = new HashSet<>();
        for(CarInfo carInfo : list) {
            noSet.add(carInfo.getCarNumber());
        }
        Map<String, List<CarInfo>> map = list.stream().collect(Collectors.groupingBy(CarInfo::getCarNumber));

        List<CarInfo> result = new ArrayList<>();

        CarTest carTest = new CarTest();
        carTest.setName("");
        carTest.setAge(0);
        carTest.setPress(0.0D);
        carTest.setTime(new Date());


        /*for(Map.Entry<String, List<CarInfo>> entry : map.entrySet()){
            String carNum = entry.getKey();
            List<CarInfo> carList = entry.getValue();
            List<CarInfo> zaibao = carList.stream().filter(u -> u.getType().equals(new Byte("1"))).collect(Collectors.toList());
            if(zaibao != null){
                if(zaibao.size() == 1){
                    //在保只有单险种
                    CarInfo carInfoZaibao = new CarInfo("AZHZG06Y1418F027744M","AZHZZ31Y1418B044438K", new Byte("1"),"QZHZG06Y1418F035995D",carNum,3);
                    result.add(carInfoZaibao);
                    System.out.println("在保里面单险种");
                } else if(zaibao.size() == 2){
                    CarInfo carInfoZaibao = new CarInfo("AZHZZ31Y1418B044438K","AZHZG06CTP18F027762O", new Byte("1"),"QZHZG06Y1418F035995D",carNum,3);
                    result.add(carInfoZaibao);
                    System.out.println("在保里面两个险种");
                }
            }
            List<CarInfo> toubao = carList.stream().filter(u -> !u.getType().equals(new Byte("1"))).collect(Collectors.toList());
            if(toubao != null){
                if(toubao.size() >= 2) {
                    if(toubao.get(0).getQuotationNo() == toubao.get(1).getQuotationNo()){
                        //一条商业一条交强
                        CarInfo zuhe = new CarInfo(toubao.get(0).getBussniessNo(),toubao.get(0).getForceNo(),new Byte("0"),toubao.get(0).getQuotationNo(),carNum,3);
                        System.out.println("投保单（询价时间从大到小排序），第一条和第二条的投保单号相同，证明包含商业险和车船税");
                        result.add(zuhe);
                    }else {
                        //1 交强
                        List<CarInfo> toubaosai1 = toubao.stream().filter(u -> u.getDeff().equals("1")).collect(Collectors.toList());
                        CarInfo shangye = new CarInfo("","",new Byte("0"),"","",1);
                        CarInfo jiaoqiang = new CarInfo("","",new Byte("0"),"","",2);
                        if(toubaosai1 != null && toubaosai1.size() > 0){
                            shangye = toubaosai1.get(0);
                        }
                        //2 商业
                        List<CarInfo> toubaosai2 = toubao.stream().filter(u -> u.getDeff().equals("2")).collect(Collectors.toList());
                        if(toubaosai2 != null && toubaosai1.size() > 0){
                            jiaoqiang = toubaosai1.get(0);
                        }
                        //商业和交强的信息
                        CarInfo zuhe = new CarInfo(shangye.getBussniessNo(),jiaoqiang.getForceNo(),new Byte("0"),jiaoqiang.getQuotationNo(),carNum,3);
                        result.add(zuhe);
                        System.out.println("交强商业组合");
                        System.out.println(zuhe.getBussniessNo() + zuhe.getForceNo());
                    }
                } else {
                    //查到投保单就只有一条
                }
            }
        }*/
        //结合Predicate使用和过滤条件筛选元素
        //Predicate<String> contain1 = n -> n.contains("LS103095");
        //Predicate<String> contain2 = n -> n.contains("2");



        //List<CarInfo> newList = list.stream().filter(contain1).collect(Collectors.toList());
    }
}
