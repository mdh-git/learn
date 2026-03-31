package com.mdh.designpattern.strategy.sell;

// --- 4. 客户端使用 ---
public class Client {
    public static void main(String[] args) {
        PromotionContext context = new PromotionContext();
        double originalPrice = 1000;

        // 618促销
        context.setStrategy(new Six18Strategy());
        System.out.println("618价格：" + context.getFinalPrice(originalPrice)); // 输出: 600.0

        // 双11促销
        context.setStrategy(new Double11Strategy());
        System.out.println("双11价格：" + context.getFinalPrice(originalPrice)); // 输出: 500.0

        // 日常销售
        context.setStrategy(new NormalStrategy());
        System.out.println("日常价格：" + context.getFinalPrice(originalPrice)); // 输出: 900.0

        // 新增“年货节”策略：只需增加一个YearEndStrategy类，无需修改现有代码
    }
}
