package com.mdh.designpattern.strategy.sell;

// --- 2. 实现具体策略 ---
// 具体策略1：618大促，打6折
class Six18Strategy implements PromotionStrategy {
    @Override
    public double calculate(double price) {
        return price * 0.6;
    }
}
