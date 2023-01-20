package com.course.apispringbootmybatis.application.controller.validation;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RegexConstants {

  public static final String EMPLOYEE_ID = "^\\d{1,10}$";
  public static final String MAIL_ADDRESS = "^(.+)@(\\S+)$";
  public static final String TELPHONE_NUMBER = "^\\d{10,20}$";


}
