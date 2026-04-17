# Mybatis插件

~~~
MyBatis 插件（Plugin）是 MyBatis 提供的一种强大的扩展机制，它允许我们在 SQL 执行的关键环节进行拦截和增强，而无需修改 MyBatis 的核心源码。这就像一个“切面”，可以在不侵入业务代码的情况下，为数据库操作添加通用功能。
~~~

## 场景
~~~
分页查询：最著名的应用，如 PageHelper 插件，可以自动拦截 SQL 并拼接上物理分页语句（如 LIMIT）。
性能监控：记录每条 SQL 语句的执行耗时，帮助定位慢查询。
数据权限：动态修改 SQL，为查询语句添加过滤条件（如 AND org_id = ?），实现数据隔离。
数据脱敏：在查询结果返回前，对敏感信息（如手机号、身份证）进行脱敏处理。
SQL 审计与优化：记录完整的 SQL 语句和参数，或进行 SQL 注入检查。
多租户/分库分表：动态修改 SQL 中的表名，以支持多租户架构或分库分表策略。
~~~

## 插件的核心原理
~~~
MyBatis 插件的本质是拦截器（Interceptor），其底层实现依赖于 JDK 动态代理。
~~~
| 拦截对象 | 作用 |
| :--- | :--- |
| Executor | SQL 执行器，负责整体的执行流程，包括增删改查和缓存管理。 |
| StatementHandler | SQL 语句处理器，负责 JDBC Statement 的创建、参数设置、SQL 执行和结果集处理。 |
| ParameterHandler | 参数处理器，负责为 `PreparedStatement` 设置参数。 |
| ResultSetHandler | 结果集处理器，负责将 JDBC 返回的 `ResultSet` 映射成 Java 对象。 |

## 工作流程
~~~
1.定义拦截器：开发者实现 Interceptor 接口，并通过 @Intercepts 和 @Signature 注解声明要拦截哪个核心对象（如 Executor）的哪个方法（如 query）。
2.生成代理：在 MyBatis 初始化时（创建 SqlSessionFactory 阶段），框架会读取所有配置的插件。当创建上述四大核心对象时，会使用 Plugin.wrap() 方法，通过 JDK 动态代理为这些对象生成代理对象。
3.链式调用：如果有多个插件，它们会形成一个拦截器链（InterceptorChain）。代理对象会按照顺序依次调用链中的每个插件的 intercept() 方法。
4.执行逻辑：在 intercept() 方法中，开发者可以编写前置处理逻辑，然后通过 invocation.proceed() 调用原始方法，最后再编写后置处理逻辑。
~~~

## 自定义插件
~~~
实现 Interceptor 接口
~~~
~~~
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import java.util.Properties;

@Intercepts({
    @Signature(
        type = Executor.class, // 拦截 Executor
        method = "query",      // 拦截 query 方法
        args = {MappedStatement.class, Object.class, org.apache.ibatis.session.RowBounds.class, org.apache.ibatis.session.ResultHandler.class} // 方法参数类型
    )
})
public class MyCustomPlugin implements Interceptor {

    // 核心拦截逻辑
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 前置逻辑：例如记录开始时间
        long startTime = System.currentTimeMillis();

        // 执行原始方法
        Object result = invocation.proceed();

        // 后置逻辑：例如记录执行耗时
        long endTime = System.currentTimeMillis();
        System.out.println("SQL执行耗时: " + (endTime - startTime) + "ms");

        return result;
    }

    // 包装目标对象，通常使用默认实现即可
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    // 设置插件属性，可从配置文件中读取
    @Override
    public void setProperties(Properties properties) {
        // 例如：String someProperty = properties.getProperty("someKey");
    }
}
~~~