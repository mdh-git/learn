package com.mdh.js;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavaScriptExecutor {

    public static void main(String[] args) throws ScriptException {
        // 创建ScriptEngine
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");

        // 执行JavaScript代码
        String jsCode = "function add(a, b) { return a + b; } add(2, 3);";
        Object result = engine.eval(jsCode);

        // 输出结果
        System.out.println("Result: " + result);
    }
}
