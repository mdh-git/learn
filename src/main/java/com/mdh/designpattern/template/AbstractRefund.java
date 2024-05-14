package com.mdh.designpattern.template;

public abstract class AbstractRefund {

    /**
     * 同款流程
     */
    protected final void doRefund(){
        // 步骤一： 申请
        this.apply();
        // 是否需要审核
        if(needVerify()){
            // 步骤二： 审核
            this.verify();
        }
        // 步骤三： 退款
        this.refundMoney();
    }

    /**
     * 申请 - 延迟到子类实现
     */
    abstract void apply();

    /**
     * 公共的审核方法
     */
    final void verify() {
        System.out.println("触发公共的审核逻辑");
    }

    /**
     * 公共的退款方法
     */
    final void refundMoney() {
        System.out.println("触发公共的退款逻辑");
    }

    /**
     * 钩子方法 - 是否需要审核
     * @return boolean
     */
    protected boolean needVerify() {
        return true;
    }

}
