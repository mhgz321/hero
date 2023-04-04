package com.keaizhale.hero.logging.annotation;

import java.lang.annotation.*;

/**
 * description: HeroLog
 * date: 2023/3/30 9:52
 * author: keaizhale
 * version: 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HeroLog {
    String name() default "";
    String operation();
}
