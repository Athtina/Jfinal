package com.example.controller;

import com.example.common.annotation.SystemLog;
import com.example.persistence.model.ChargingDayRecord;
import com.example.persistence.service.ChargingDayRecordService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  @Autowired
  private ChargingDayRecordService cdrService;
  @SystemLog(message = "登录")
  @RequestMapping(value = "/login",method = RequestMethod.GET)
  public String hello(){
    return "hello spring!";
  }

  @RequestMapping(value = "/hello",method = RequestMethod.GET)
  public List<ChargingDayRecord> say(){
    return cdrService.getData();
  }
}

