package com.mdh.designpattern.template;

/**
 * 用户申请退款实现类
 */
public class UserRefund extends AbstractRefund {

    /**
     * 申请差异化，子类自己实现
     */
    @Override
    void apply() {
        System.out.println("触发用户申请退款逻辑");
    }
}
