package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
//  @Value("${name}")
//  private String name;

  @RequestMapping(value = "/login",method = RequestMethod.GET)
  public String hello(){
    return "hello spring!";
  }

//  @RequestMapping(value = "/hello",method = RequestMethod.GET)
//  public String say(){
//    return name;
//  }
}

