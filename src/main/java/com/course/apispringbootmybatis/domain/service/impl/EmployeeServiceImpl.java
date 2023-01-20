package com.course.apispringbootmybatis.domain.service.impl;

import com.course.apispringbootmybatis.application.controller.message.EmployeeRequest;
import com.course.apispringbootmybatis.application.exception.EmployeeNotFoundException;
import com.course.apispringbootmybatis.domain.dto.EmployeeDto;
import com.course.apispringbootmybatis.domain.entity.EmployeeEntity;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import com.course.apispringbootmybatis.domain.entity.PersonalDataEntity;
import com.course.apispringbootmybatis.domain.service.EmployeeService;
import com.course.apispringbootmybatis.domain.service.logic.EmployeeLogicService;
import com.course.apispringbootmybatis.infrastructure.mapper.EmployeeMapper;
import java.util.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final ModelMapper modelMapper;
  private final EmployeeLogicService service;
  private final EmployeeMapper employeeMapper;

  @Override
  public EmployeeDto selectById(Integer employeeId) {
    return this.employeeMapper.selectById(employeeId)
        .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
  }

  @Override
  public List<EmployeeDto> selectAll() {
    return this.employeeMapper.selectAll();
  }

  @Override
  public EmployeeDto create(EmployeeRequest request) {
    var employee = this.modelMapper.map(request, EmployeeEntity.class);
    var personal = this.modelMapper.map(request, PersonalDataEntity.class);
    var history = this.modelMapper.map(request, HistoryEntity.class);
    // 社員情報の登録
    this.service.insert(employee, personal, history);
    // 登録した情報の検索して返却
    return this.selectById(employee.getEmployeeId());
  }

  @Override
  public EmployeeDto update(EmployeeRequest request) {
    return EmployeeDto.builder().build();
  }
}
