package com.mdh.designpattern.strategy.sell;

// 具体策略3：日常销售，打9折
class NormalStrategy implements PromotionStrategy {
    @Override
    public double calculate(double price) {
        return price * 0.9;
    }
}
