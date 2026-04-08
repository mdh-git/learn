# SpringBoot自动装配原理

~~~
自动装配是Spring Boot的核心功能，核心目的是约定优于配置，通过智能判断项目依赖和环境，自动将组件注册到IOC容器


@SpringBootApplication

包含三个注解
@SpringBootConfiguration：该注解与@Configuration注解作用相同，用来声明当前也是一个配置类。
@EnableAutoConfiguration ：SpringBoot实现自动化配置的核心注解。
@ComponentScan(        :组件扫描，默认扫描当前引导类所在包及其子包。
        excludeFilters = {@Filter(
        type = FilterType.CUSTOM,
        classes = {TypeExcludeFilter.class}
    ), @Filter(
        type = FilterType.CUSTOM,
        classes = {AutoConfigurationExcludeFilter.class}
    )}
)


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import({AutoConfigurationImportSelector.class})  加载META-INF下的spring.factories文件到容器中
public @interface EnableAutoConfiguration {
    String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration";

    Class<?>[] exclude() default {};

    String[] excludeName() default {};
}
~~~

~~~
在spring.factories文件中有很多XXXAutoConfiguration


# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\


以RedisAutoConfiguration为例

@Configuration(  是一个配置类
    proxyBeanMethods = false   
)
@ConditionalOnClass({RedisOperations.class}) 判断是否有对应的字节码
@EnableConfigurationProperties({RedisProperties.class})
@Import({LettuceConnectionConfiguration.class, JedisConnectionConfiguration.class})
public class RedisAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(
        name = {"redisTemplate"}  判断环境中没有对应的bean
    )
    @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
~~~

~~~
1，在Spring Boot项目中的引导类上有一个注解@SpringBootApplication，这个注解是对三个注解进行了封装，
        分别是:@SpringBootConfiguration
              @EnableAutoConfiguration
              @ComponentScan
2，其中@EnableAutoConfiguration是实现自动化配置的核心注解。该注解通过@lmport注解导入对应的配置选择器。
    内部就是读取了该项目和该项目引用的Jar包的的classpath路径下META-INF/spring.factories文件中的所配置的类的全类名。
    在这些配置类中所定义的Bean会根据条件注解所指定的条件来决定是否需要将其导入到Spring容器中。
    
3,条件判断会有像@ConditionalOnClass这样的注解，判断是否有对应的class文件，如果有则加载该类，把这个配置类的所有的Bean放入spring容器中使用。
~~~
~~~
@SpringBootApplication → 启动应用，触发 @EnableAutoConfiguration；
AutoConfigurationImportSelector → 从配置文件加载候选自动配置类；
@Conditional → 判断哪些自动配置类生效；
BeanFactory → 根据自动配置类中的 @Bean 定义创建组件（实例化 → 注入 → 初始化 → 注册）；
@ConditionalOnMissingBean → 允许开发者自定义 Bean 覆盖默认组件。
~~~



## 自动装配
~~~
1. 自动装配入口：@SpringBootApplication注解
   Spring Boot 主启动类通常标注 @SpringBootApplication。
   这是一个 组合注解，包含：
        @SpringBootConfiguration（相当于 @Configuration）；
        @EnableAutoConfiguration（开启自动装配的关键）；
        @ComponentScan（扫描业务组件并放入 IOC 容器）。
    真正触发自动装配逻辑的，是 @EnableAutoConfiguration。
    
2. 自动装配核心：
   AutoConfigurationImportSelector
        在 @EnableAutoConfiguration 内部，通过：@Import(AutoConfigurationImportSelector.class)
        将 AutoConfigurationImportSelector 导入容器。
        该类的核心方法：
            getCandidateConfigurations() → 从配置文件中读取所有候选的自动配置类。
            
3. 自动配置类的来源
   Spring Boot 2.9：候选类在
        spring-boot-autoconfigure 包的 META-INF/spring.factories 文件中；

   Spring Boot 2.9之后 3.x：迁移到
        META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports。

    文件中列出了大量自动配置类（例如：DataSourceAutoConfiguration、RedisAutoConfiguration）。
    
4. 自动配置类 是否生效：
   @Conditional  系列注解
   并不是所有配置类都会生效，Spring Boot 会检查自动配置类上的条件注解：
        @ConditionalOnClass：类路径下存在某个类时才生效；
        @ConditionalOnProperty：配置文件中存在指定属性时才生效；
        @ConditionalOnMissingBean：容器中没有某个 Bean 时才生效；
        @ConditionalOnWebApplication：当前环境是 Web 应用时才生效。
    只有满足所有条件的配置类，才会被加载。
    
5. Bean 的创建：BeanFactory执行注册逻辑
   一旦某个自动配置类通过条件判断，Spring 会把它交给 BeanFactory 管理。
   BeanFactory 会根据配置类中定义的 @Bean 方法来创建组件：
        实例化 Bean（反射创建对象）；
        依赖注入（解析并注入所需依赖）；
        初始化（执行 @PostConstruct、InitializingBean、init-method 等）；
        放入单例池（若是单例 Bean）。
    因此，自动配置类本质上就是告诉 BeanFactory：在满足条件时，如何去创建和管理这些 Bean。
    
6. 自定义组件覆盖默认组件：@ConditionalOnMissingBean
   Spring Boot 的默认组件通常在 @Bean 方法上标注了 @ConditionalOnMissingBean。
   意味着：如果容器中已有该类型 Bean，则默认 Bean 不会再创建。
   覆盖方式：
        在自定义配置类（@Configuration）中，通过 @Bean 定义自己的 Bean；
        由于已有自定义 Bean，默认配置类中的 @ConditionalOnMissingBean 不满足；
        最终 IOC 容器中保留的就是开发者定义的 Bean。

例子：覆盖默认 RedisTemplate
@Configuration
public class MyRedisConfig {
        @Bean
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(factory);
            // 自定义序列化规则
            return template;
        }
}
~~~


## 总结（流程简化版）
~~~
@SpringBootApplication → 启动应用，触发 @EnableAutoConfiguration；
AutoConfigurationImportSelector → 从配置文件加载候选自动配置类；
@Conditional → 判断哪些自动配置类生效；
BeanFactory → 根据自动配置类中的 @Bean 定义创建组件（实例化 → 注入 → 初始化 → 注册）；
@ConditionalOnMissingBean → 允许开发者自定义 Bean 覆盖默认组件。
~~~
