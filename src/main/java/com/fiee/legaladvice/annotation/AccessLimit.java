package com.fiee.legaladvice.annotation;

import java.lang.annotation.*;

/**
 * @Author: Fiee
 * @ClassName: AccessLimit
 * @Date: 2023/3/30
 * @Version: v1.0.0
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {
    /**
     * 单位时间
     * @return
     */
    int seconds() default 60;

    /**
     * 单位时间操作次数
     * @return
     */
    int count() default 10;

    /**
     * 返回信息
     * @return
     */
    String massage() default "操作频繁，请稍后再试";
}
