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