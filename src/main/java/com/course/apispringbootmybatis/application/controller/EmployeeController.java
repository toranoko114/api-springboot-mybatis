package com.course.apispringbootmybatis.application.controller;

import com.course.apispringbootmybatis.application.controller.message.EmployeeRequest;
import com.course.apispringbootmybatis.application.controller.message.EmployeeResponse;
import com.course.apispringbootmybatis.application.controller.validation.RegexConstants;
import com.course.apispringbootmybatis.domain.service.EmployeeService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 社員情報APIのコントローラー
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

  private final EmployeeService service;
  private final ModelMapper modelMapper;

  @GetMapping("/{employeeId}")
  // UTで文字種のテストをする
  public EmployeeResponse fetchEmployee(
      @PathVariable(value = "employeeId")
      @Pattern(regexp = RegexConstants.EMPLOYEE_ID) Integer employeeId) {
    return this.modelMapper.map(service.selectById(employeeId), EmployeeResponse.class);
  }

  @GetMapping("/")
  // UTで文字種のテストをする
  public List<EmployeeResponse> fetchEmployees() {
    var employeeDtoList = service.selectAll();
    return List.of(this.modelMapper.map(employeeDtoList, EmployeeResponse[].class));
  }

  @PostMapping("/")
  public EmployeeResponse createEmployee(@Valid @RequestBody EmployeeRequest request) {
    return this.modelMapper.map(service.create(request), EmployeeResponse.class);
  }

  @PatchMapping("/{employeeId}")
  public EmployeeResponse updateEmployee(
      @PathVariable(value = "employeeId")
      @Pattern(regexp = RegexConstants.EMPLOYEE_ID) Integer employeeId,
      @Valid @RequestBody EmployeeRequest request) {
    return this.modelMapper.map(service.update(employeeId, request), EmployeeResponse.class);
  }


}
