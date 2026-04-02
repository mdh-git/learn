package com.mdh.designpattern.strategy.sell;

// 具体策略2：双11大促，打5折
class Double11Strategy implements PromotionStrategy {
    @Override
    public double calculate(double price) {
        return price * 0.5;
    }
}
