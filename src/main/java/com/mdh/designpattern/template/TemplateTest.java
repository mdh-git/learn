package com.mdh.designpattern.template;

public class TemplateTest {

    public static void main(String[] args) {
        System.out.println("测试 用户退款 开始\n");
        AbstractRefund userRefund = new UserRefund();
        userRefund.doRefund();
        System.out.println("\n测试 用户退款 结束");


        System.out.println("测试 后台运营退款（需要审核） 开始\n");
        AbstractRefund adminRefund = new AdminRefund(true);
        adminRefund.doRefund();
        System.out.println("\n测试 后台运营退款（需要审核） 结束");


        System.out.println("测试 后台运营退款（不需要审核） 开始\n");
        AbstractRefund adminRefund1 = new AdminRefund(false);
        adminRefund1.doRefund();
        System.out.println("\n测试 后台运营退款（不需要审核） 结束");

    }
}
