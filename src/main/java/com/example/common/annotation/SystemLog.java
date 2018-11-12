package com.example.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author yanli.zhang
 * @dateTime 2018/11/8 17:04
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SystemLog {
  Type type() default Type.SEARCH;

  String message() default "";

  boolean isInsertTable() default false;

  public enum Type {
    ADD, UPDATE, DELETE, SEARCH, INDEX;
  }
}
