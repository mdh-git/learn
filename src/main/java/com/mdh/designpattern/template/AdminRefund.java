package com.mdh.designpattern.template;

/**
 * 后台运营申请退款实现类
 */
public class AdminRefund extends AbstractRefund {

    /**
     * 默认需要审核
     */
    private boolean needVerify = true;

    /**
     * 构造方法判断是否需要审核
     *
     * @param needVerify 需要验证
     */
    public AdminRefund(boolean needVerify) {
        this.needVerify = needVerify;
    }

    /**
     * 申请差异化，子类自己实现
     */
    @Override
    void apply() {
        System.out.println("触发用户申请退款逻辑");
    }

    /**
     * 钩子方法 - 是否需要审核
     *
     * @return boolean
     */
    @Override
    protected boolean needVerify() {
        return this.needVerify;
    }

}
