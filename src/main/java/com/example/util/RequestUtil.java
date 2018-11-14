package com.example.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author yanli.zhang
 * @dateTime 2018/11/14 10:02
 */
public class RequestUtil {
  /**
   * 获取ip地址
   *
   * @param request
   * @return
   */
  public static String getRemoteAddr(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    if (ip.split(",").length > 1) {
      ip = ip.split(",")[0];
    }
    return ip;
  }


  /**
   * 对uri中可能出现的多个斜杠去重
   *
   * @param request
   * @return
   */
  public static String trimURI(HttpServletRequest request) {
    String uri = request.getRequestURI();
    StringBuffer sb = new StringBuffer();
    boolean flag = false;
    for (int i = 0; i < uri.length(); i++) {
      char c = uri.charAt(i);
      if (c == '/') {
        if (flag) {
          continue;
        }
        flag = true;
      } else {
        flag = false;
      }
      sb.append(c);
    }
    return sb.toString();
  }

}
