package com.example.basic;

import com.example.util.DateTimeHelper;
import com.example.util.EnvironmentHelper;
import com.example.util.JsonHelper;
import com.example.util.MessageUtil;
import com.example.util.VO;
import java.util.Date;
import java.util.HashMap;
import org.springframework.util.Assert;

/**
 * @Author yanli.zhang
 * @dateTime 2018/11/14 13:54
 */
public class ESBResult  extends HashMap<String, String> implements VO {
  public final static String SUCCESS = "WL-0000";
  public final static String ERROR = "WL-0001";

  private static String esbSecret = EnvironmentHelper.getProperty("esb.secret");

  private ESBResult() {
    super();
    put("server_time", DateTimeHelper.dateToString(new Date(), DateTimeHelper.YYYY_MM_DD_HH_MM_SS));
  }

  public static ESBResult success(String application, Object data) {
    Assert.notNull(application, "ESBResult中application不能为空");
    ESBResult result = new ESBResult();
    result.put("application", application);
    result.put("data", JsonHelper.parseToJson(data));
    result.put("result_code", SUCCESS);
    result.put("result_msg", "成功");
    result.put("sign", MessageUtil.sign(result, esbSecret));
    return result;
  }

  public static ESBResult success(String application) {
    return success(application, null);
  }

  public static ESBResult error(String application, Object data, String message) {
    Assert.notNull(application, "ESBResult中application不能为空");
    ESBResult result = new ESBResult();
    result.put("application", application);
    result.put("data", JsonHelper.parseToJson(data));
    result.put("result_code", ERROR);
    result.put("result_msg", message);
    result.put("sign", MessageUtil.sign(result, esbSecret));
    return result;
  }

  public static ESBResult error(String application, String message) {
    return error(application, null, message);
  }

  public static ESBResult error(String application) {
    return error(application, null, null);
  }

  @Override
  public String toString() {
    return toJson();
  }
}
