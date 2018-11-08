package com.example.controller;

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

  @RequestMapping(value = "/login",method = RequestMethod.GET)
  public String hello(){
    return "hello spring!";
  }

  @RequestMapping(value = "/hello",method = RequestMethod.GET)
  public List<ChargingDayRecord> say(){
    return cdrService.getData();
  }
}

