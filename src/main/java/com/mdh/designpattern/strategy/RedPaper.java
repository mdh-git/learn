package com.mdh.designpattern.strategy;

/**
 * @Author: madonghao
 * @Date: 2022/1/25 5:52 下午
 */
public class RedPaper extends Strategy {

	@Override
	public String query() {
		return "红包";
	}
}
