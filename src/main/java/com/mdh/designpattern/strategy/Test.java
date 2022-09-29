package com.mdh.designpattern.strategy;

/**
 * @Author: madonghao
 * @Date: 2022/1/25 5:54 下午
 */
public class Test {

  public static void main(String[] args) {

    String grantType;
    String resourceType = "购物券";
    switch (resourceType) {
      case "红包":
        //grantType = new Context(new RedPaper()).contextInterface();
        break;
      case "购物券":
        //grantType = new Context(new Shopping()).contextInterface();
        break;
      default:
        grantType = "其他";
        break;
    }

    //System.out.println(grantType);
  }

}
