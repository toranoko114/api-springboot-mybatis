package com.course.api.springboot.mybatis.application.controller;

import com.course.api.springboot.mybatis.application.controller.message.EmployeeRequest;
import com.course.api.springboot.mybatis.application.controller.message.EmployeeResponse;
import com.course.api.springboot.mybatis.domain.service.interfaces.EmployeeService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 社員情報APIのコントローラー
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class CompanyController {

  @Qualifier("companyEmployeeServiceImpl")
  private final EmployeeService service;
  private final ModelMapper modelMapper;

  @GetMapping("/{employeeId}")
  public ResponseEntity<EmployeeResponse> fetchEmployee(
      @PathVariable(value = "employeeId") String employeeId) {
    return ResponseEntity.ok()
        .body(this.modelMapper.map(service.selectById(employeeId), EmployeeResponse.class));
  }

  @GetMapping("/")
  public ResponseEntity<List<EmployeeResponse>> fetchEmployees() {
    var employeeDtoList = service.selectAll();
    return ResponseEntity.ok()
        .body(List.of(this.modelMapper.map(employeeDtoList, EmployeeResponse[].class)));
  }

  @PostMapping("/")
  public ResponseEntity<EmployeeResponse> createEmployee(
      @Valid @RequestBody EmployeeRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.modelMapper.map(service.create(request), EmployeeResponse.class));
  }

  @PutMapping("/{employeeId}")
  public ResponseEntity<EmployeeResponse> updateEmployee(
      @PathVariable(value = "employeeId") String employeeId,
      @Valid @RequestBody EmployeeRequest request) {
    return ResponseEntity.ok()
        .body(this.modelMapper.map(service.update(employeeId, request), EmployeeResponse.class));
  }

  @DeleteMapping("/{employeeId}")
  public ResponseEntity<Void> deleteById(
      @PathVariable(value = "employeeId") String employeeId) {
    service.deleteById(employeeId);
    return ResponseEntity.noContent().build();
  }

}
