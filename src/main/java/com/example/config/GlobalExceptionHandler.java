package com.example.config;

import com.example.basic.ESBResult;
import com.example.basic.Result;
import com.example.basic.ResultCode;
import com.example.common.ESBException;
import com.example.util.RequestUtil;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @Author yanli.zhang
 * @dateTime 2018/11/14 9:38
 */
public class GlobalExceptionHandler {


  private final Logger logger = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(value = Throwable.class)
  @ResponseBody
  public Object handleException(HttpServletRequest request, HttpServletResponse response, Throwable exception) {
      String ip = RequestUtil.getRemoteAddr(request);
      String uri = RequestUtil.trimURI(request);
      logger.error(String.format("ip地址%s,访问地址%s", ip, uri), exception);
      Result result = Result.result(ResultCode.RESULT__ERROR_1);
      // http请求方法异常，抛出异常信息;request绑定参数异常，抛出异常信息;servlet异常抛出
      if (exception instanceof ServletException) {
        result.setResultDesc(exception.getLocalizedMessage());
      }
      // 参数错误IllegalArgumentException
      else if (exception instanceof IllegalArgumentException || exception.getCause() instanceof IllegalArgumentException) {
        result.setResultCode(ResultCode.RESULT__ERROR_2);
        result.setResultDesc(exception.getCause().getMessage());
      }
      // request body错误
      else if (exception instanceof HttpMessageConversionException) {
        String desc = exception.getLocalizedMessage();
        if (desc != null && desc.contains(":")) {
          desc = desc.substring(0, desc.indexOf(":"));
        }
        result.setResultDesc(desc);
      }
      // 方法中参数转换错误
      else if (exception instanceof MethodArgumentTypeMismatchException) {
        result.setResultDesc(String.format("参数%s错误", ((MethodArgumentTypeMismatchException) exception).getName()));
      }
      // ESB调用错误
      else if (exception instanceof ESBException) {
        return ESBResult.error(((ESBException) exception).getApplication(), exception.getLocalizedMessage());
      }
      // 参数校验错误
      else if (exception instanceof BindException) {
        BindException bindException = (BindException) exception;
        result.setResultDesc(bindException.getFieldError().getDefaultMessage());
      }
      // db错误
      else if (exception instanceof SQLException || exception.getCause() instanceof SQLException) {
        result.setResultDesc("db错误");
      }
      return result;
    }

}
