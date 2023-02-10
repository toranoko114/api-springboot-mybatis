package com.course.api.springboot.mybatis.application.controller;

import com.course.api.springboot.mybatis.application.controller.message.EmployeeRequest;
import com.course.api.springboot.mybatis.application.controller.message.EmployeeResponse;
import com.course.api.springboot.mybatis.domain.service.interfaces.EmployeeService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 子会社 社員情報APIのコントローラー
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sub/employee")
public class SubCompanyController {

  @Qualifier("subCompanyEmployeeServiceImpl") private final EmployeeService service;
  private final ModelMapper modelMapper;

  @GetMapping("/{employeeId}")
  public EmployeeResponse fetchEmployee(
      @PathVariable(value = "employeeId") String employeeId) {
    return this.modelMapper.map(service.selectById(employeeId), EmployeeResponse.class);
  }

  @GetMapping("/")
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
      @PathVariable(value = "employeeId") String employeeId,
      @Valid @RequestBody EmployeeRequest request) {
    return this.modelMapper.map(service.update(employeeId, request), EmployeeResponse.class);
  }

  @DeleteMapping("/{employeeId}")
  public ResponseEntity<Void> deleteById(
      @PathVariable(value = "employeeId") String employeeId) {
    service.deleteById(employeeId);
    return ResponseEntity.noContent().build();
  }

}
