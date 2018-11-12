package com.example.common.annotation;

/**
 * @Author yanli.zhang
 * @dateTime 2018/11/8 17:04
 */
public @interface SystemLog {
  Type type() default Type.SEARCH;

  String message() default "";

  boolean isInsertTable() default false;

  public enum Type {
    ADD, UPDATE, DELETE, SEARCH, INDEX;
  }
}
