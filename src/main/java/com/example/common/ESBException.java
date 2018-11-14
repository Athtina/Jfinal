package com.example.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author yanli.zhang
 * @dateTime 2018/11/14 13:45
 */
@Setter
@Getter
public class ESBException extends Exception {

  private String application;

  public ESBException(String application) {
    super();
    this.application = application;
  }

  public ESBException(String application, String message) {
    super(message);
    this.application = application;
  }

  public ESBException(String application, Throwable cause) {
    super(cause);
    this.application = application;
  }

  public ESBException(String application, String message, Throwable cause) {
    super(message, cause);
    this.application = application;
  }
}
