package com.keaizhale.hero.logging.aspect;

import com.keaizhale.hero.logging.annotation.HeroLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * description: HeroLogAspect
 * date: 2023/3/30 10:01
 * author: keaizhale
 * version: 1.0
 */
@Aspect
public class HeroLogAspect {
    @Value("${spring.application.name}")
    private String applicationName;
    private static final Logger log = LoggerFactory.getLogger(HeroLogAspect.class);
    /**
     * 用于SpEL表达式解析.
     */
    private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Pointcut("@annotation( com.keaizhale.hero.logging.annotation.HeroLog)")
    public void pre() {
    }

    @Around("pre()")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        Object[] args = jp.getArgs();
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        String[] parameterNames = methodSignature.getParameterNames();
//        Map<String, Object> paramNameValueMap = Map.of();
//        for (int i = 0; i < parameterNames.length; i++) {
//            paramNameValueMap.put(parameterNames[i], args[i]);
//        }
        HeroLog heroLog = method.getAnnotation(HeroLog.class);

        String operation = heroLog.operation();
        if (operation.contains("#")) {
            //获取方法参数值
//            Object[] args = joinPoint.getArgs();
            operation = getValBySpEL(operation, methodSignature, args);
        }
        log.debug("应用：{}，日志名称：{}, 日志信息：{}", applicationName, heroLog.name(), operation);
        return jp.proceed();
    }


    /**
     * 解析spEL表达式
     */
    private String getValBySpEL(String spEL, MethodSignature methodSignature, Object[] args) {
        //获取方法形参名数组
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
        if (paramNames != null && paramNames.length > 0) {
            Expression expression = spelExpressionParser.parseExpression(spEL);
            // spring的表达式上下文对象
            EvaluationContext context = new StandardEvaluationContext();
            // 给上下文赋值
            for(int i = 0; i < args.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
            return expression.getValue(context).toString();
        }
        return null;
    }

}
