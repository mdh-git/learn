package com.mdh.designpattern.strategy.sell;

// --- 1. 定义策略接口 ---
// 策略接口：定义统一的折扣计算规则
public interface PromotionStrategy {

    double calculate(double price);
}
