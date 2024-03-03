package com.fiee.legaladvice.annotation;

import java.lang.annotation.*;

/**
 * @Author: Fiee
 * @ClassName: OptLog
 * @Date: 2024/3/2
 * @Version: v1.0.0
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {
    /**
     *操作类型
     * @return
     */
    String optType() default "";
}
