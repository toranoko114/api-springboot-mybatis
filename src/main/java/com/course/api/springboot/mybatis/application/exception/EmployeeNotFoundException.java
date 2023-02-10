package com.course.api.springboot.mybatis.application.exception;

public class EmployeeNotFoundException extends RuntimeException {

  public EmployeeNotFoundException(String employeeId) {
    super("Employee not found with employeeId:" + employeeId);
  }

}
