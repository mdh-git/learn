package com.mdh.designpattern.strategy.one;

import com.mdh.designpattern.strategy.Strategy;

/**
 * @Author: madonghao
 * @Date: 2022/1/25 5:53 下午
 */
public class Shopping extends Strategy {
	@Override
	public String query() {
		return "购物";
	}
}
