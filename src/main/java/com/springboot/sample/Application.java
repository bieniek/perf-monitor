package com.springboot.sample;

import org.aopalliance.aop.Advice;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Configuration
@EnableAspectJAutoProxy
public class Application {
    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Pointcut("execution(public * *(..))")
    public void anyPublicOperation() {}

    @Pointcut("within(com.springboot.sample..*)")
    public void inMyPackage() {}

    @Pointcut("anyPublicOperation() && inMyPackage()")
    public void all() {}

    @Bean
    public ExampleSpringService exampleSpringService() {
        return new ExampleSpringService();
    }

    @Bean
    public Advisor performanceMonitorAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("com.springboot.sample.Application.all()");
        return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor());
    }

    @Bean
    public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
        return new PerformanceMonitorInterceptor();
    }
}
