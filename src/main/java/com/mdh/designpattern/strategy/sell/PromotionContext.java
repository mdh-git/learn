package com.mdh.designpattern.strategy.sell;

// --- 3. 定义上下文 ---
// 上下文：统一调用入口，动态切换策略
class PromotionContext {
    private PromotionStrategy strategy;

    // 动态设置策略
    public void setStrategy(PromotionStrategy strategy) {
        this.strategy = strategy;
    }

    // 统一计算入口
    public double getFinalPrice(double price) {
        return strategy.calculate(price);
    }
}
