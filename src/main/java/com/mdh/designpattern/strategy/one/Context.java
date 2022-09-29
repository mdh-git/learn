package com.mdh.designpattern.strategy.one;

import com.mdh.designpattern.strategy.Strategy;

/**
 * @Author: madonghao
 * @Date: 2022/1/25 5:48 下午
 */
public class Context {

	Strategy strategy;

	public Context(Strategy strategy){
		this.strategy = strategy;
	}

	public String contextInterface(){
		return strategy.query();
	}
}
