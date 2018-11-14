package com.example.basic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * @Author yanli.zhang
 * @dateTime 2018/11/14 13:11
 */
@Setter
@Getter
public class Result {
  private String requestId;
  private String resultCode;
  private String resultDesc;
  private Long resultServerTime;
  private Object resultData;


  public static Result successResult() {
    return result(ResultCode.RESULT_SUCCESS);
  }

  public static Result successResult(Object data) {
    Result result = successResult();
    result.setResultData(data);
    return result;
  }

  public static Result result(String resultCode) {
    return result(resultCode, ResultCode.getValueByKey(resultCode));
  }

  public static Result result(String resultCode, String desc) {
    Result result = new Result();
    result.setResultCode(resultCode);
    result.setResultDesc(desc);
    return result;
  }

  public static Result result(String resultCode, BindingResult bindingResult) {
    List<ObjectError> allErrors = bindingResult.getAllErrors();
    StringBuilder sb = new StringBuilder();
    if (CollectionUtils.isNotEmpty(allErrors)) {
      for (ObjectError oe : allErrors) {
        sb.append(oe.getDefaultMessage()).append(";");
      }
    }
    return result(resultCode, sb.substring(0, sb.length() - 1));
  }

  @JsonIgnore
  public boolean isError() {
    return !isSuccess();
  }

  @JsonIgnore
  public boolean isSuccess() {
    return ResultCode.RESULT_SUCCESS.equals(resultCode);
  }

  public Long getResultServerTime() {
    return System.currentTimeMillis();
  }

  public void setResultServerTime(Long resultServerTime) {
    this.resultServerTime = resultServerTime;
  }


}
