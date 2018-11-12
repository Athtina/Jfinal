package com.example.common.aspect;

import com.example.common.annotation.SystemLog;
import com.example.util.JsonHelper;
import com.example.util.ReflectUtils;
import com.example.util.TemplateUtils;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @Author yanli.zhang
 * @dateTime 2018/11/8 17:09
 */
@Aspect
@Component
public class SystemLogAspect {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Pointcut("@annotation(com.example.common.annotation.SystemLog)")
  public void contrllerAspt(){}

  @Around("contrllerAspt()")
  public Object doBefore(ProceedingJoinPoint joinPoint){
    Object obj = null;
    long startTime = System.currentTimeMillis();
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();// 获取连接点的方法签名对象；
    Method method = signature.getMethod();
    String methodName = signature.getName();
    Object controller = joinPoint.getTarget();// 获取连接点所在的目标对象；
    String controllerName = controller.getClass().getName();
    Map<String, Object> valueMap = getControllerMethodDescription(joinPoint, method, controller);// 参数值
    SystemLog myLogger = method.getAnnotation(SystemLog.class);// 获取注解
    String message = myLogger.message();// 注解上的值
    message = TemplateUtils.getValue(message, valueMap);
    logger.info(controllerName + "-" + methodName + message + "参数params={" + valueMap + "}");
    try {
      obj = joinPoint.proceed();
      if(myLogger.isInsertTable()) {
        //插入表
        if("ADD".equals(String.valueOf(myLogger.type()))) {
          System.err.println("新增存表......");
        }else if("UPDATE".equals(String.valueOf(myLogger.type()))) {
          System.err.println("更新存表......");
        }else if("DELETE".equals(String.valueOf(myLogger.type()))) {
          System.err.println("删除存表......");
        }
      }
      long endTime = System.currentTimeMillis();
      logger.info(controllerName + "-" + methodName + message + "成功,耗时time={" + (endTime - startTime)
          + "毫秒},返回值result={" + JsonHelper.parseToJson(obj) + "}");
    } catch (Throwable e) {
      logger.info(controllerName + "-" + methodName + message + "失败,错误信息error={" + e.getMessage() + "}");
      throw new RuntimeException(e);
    }
    return obj;

  }

  @SuppressWarnings("rawtypes")
  private Map<String, Object> getControllerMethodDescription(ProceedingJoinPoint joinPoint, Method method, Object action){
    Map<String, Object> valueMap = new HashMap<String, Object>();
    try {
      Object[] args = joinPoint.getArgs();
      String[] argsNames = ReflectUtils.getMethodParameterNamesByAsm4(action.getClass(), method);
      if (argsNames == null || argsNames.length == 0) {
        return valueMap;
      } else if (args == null || args.length == 0) {
        return valueMap;
      }
      for (int i = 0; i < args.length; i++) {
        valueMap.put(argsNames[i], JsonHelper.parseToJson(args[i]));
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return valueMap;
  }
}
