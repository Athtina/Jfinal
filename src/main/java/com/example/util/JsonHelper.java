package com.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author yanli.zhang
 * @dateTime 2018/11/12 9:47
 */
public class JsonHelper {

  private static ObjectMapper objectMapper = new ObjectMapper();

  public static String parseToJson(Object o) {
    if (o == null) {
      return null;
    }
    try {
      return objectMapper.writeValueAsString(o);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
