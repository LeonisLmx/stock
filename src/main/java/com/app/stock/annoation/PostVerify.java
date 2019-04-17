package com.app.stock.annoation;

import java.lang.annotation.*;

/**
 * Created by lmx
 * Date 2019/1/9
 * 用于对头部信息进行验证的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface PostVerify{
}
