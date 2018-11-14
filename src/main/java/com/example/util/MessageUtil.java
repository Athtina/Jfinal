package com.example.util;

import com.nextev.util.CertUtil;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author yanli.zhang
 * @dateTime 2018/11/14 13:56
 */
public class MessageUtil {
  /**
   * 得到签名
   *
   * @param map
   * @param secretKey
   * @return
   */
  public static String sign(Map<String, ? extends Object> map, String secretKey) {
    StringBuilder contentBuffer = new StringBuilder();
    Object[] signParamArray = map.keySet().toArray();
    Arrays.sort(signParamArray);
    for (Object key : signParamArray) {
      String value = Objects.toString(map.get(key), null);
      if (!"digest".equals(key) && StringUtils.isNotEmpty(value)) {
        contentBuffer.append(key).append("=").append(value).append("&");
      }
    }
    contentBuffer.delete(contentBuffer.length() - 1, contentBuffer.length()).append(secretKey);
    return CertUtil.Md5(contentBuffer.toString());
  }

}
