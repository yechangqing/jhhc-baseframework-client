package com.jhhc.baseframework.client.listener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通知监听的方法上，标注此注解和名称
 * 比如 @Notify({"position","detail"})
 *
 * @author yecq
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Notify {

    public String[] value() default {""};
}
