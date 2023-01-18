package com.course.apispringbootmybatis.application.controller;

import com.course.apispringbootmybatis.application.controller.message.EmployeeRequest;
import com.course.apispringbootmybatis.application.controller.message.EmployeeResponse;
import com.course.apispringbootmybatis.domain.service.EmployeeService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

  private final EmployeeService service;
  private final ModelMapper modelMapper;

  @PostMapping("/")
  public EmployeeResponse createEmployee(
      @Valid @RequestBody EmployeeRequest request) {
    return this.modelMapper.map(service.create(request), EmployeeResponse.class);
  }


}
