package com.example.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

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

  public static Map<String, String> parseToMapStrStr(String json) {
    return parseToObject(json, new TypeReference<Map<String, String>>() {
    });
  }

  /**
   * 此方法可以用于复杂对象比如，List<Account>，其他方法返回的则是List<Map>
   */
  public static <T> T parseToObject(String json, TypeReference<T> type) {
    try {
      return objectMapper.readValue(json, type);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
