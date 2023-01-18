package com.course.apispringbootmybatis.application.exception;

public class EmployeeNotFoundException extends RuntimeException {

  private Integer employeeId;

  public EmployeeNotFoundException(Integer employeeId) {
    super("Employee not found with employeeId:" + employeeId);
    this.employeeId = employeeId;
  }

}
