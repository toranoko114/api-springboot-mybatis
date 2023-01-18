package com.course.apispringbootmybatis.domain.service;

import com.course.apispringbootmybatis.application.controller.message.EmployeeRequest;
import com.course.apispringbootmybatis.domain.dto.EmployeeDto;
import java.util.List;

public interface EmployeeService {

  EmployeeDto selectById(Integer userId);

  List<EmployeeDto> selectAll();

  EmployeeDto create(EmployeeRequest request);

  EmployeeDto update(EmployeeRequest request);



}
