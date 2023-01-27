package com.course.apispringbootmybatis.domain.service.impl;

import com.course.apispringbootmybatis.application.controller.message.EmployeeRequest;
import com.course.apispringbootmybatis.application.exception.EmployeeNotFoundException;
import com.course.apispringbootmybatis.domain.dto.EmployeeDto;
import com.course.apispringbootmybatis.domain.entity.EmployeeEntity;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import com.course.apispringbootmybatis.domain.entity.PersonalEntity;
import com.course.apispringbootmybatis.domain.service.EmployeeService;
import com.course.apispringbootmybatis.domain.service.logic.EmployeeLogic;
import com.course.apispringbootmybatis.infrastructure.mapper.EmployeeMapper;
import java.util.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * 社員情報APIのサービス実装クラス
 */
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final ModelMapper modelMapper;
  private final EmployeeLogic logic;
  private final EmployeeMapper employeeMapper;

  @Override
  public EmployeeDto selectById(String employeeId) {
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
    var personal = this.modelMapper.map(request, PersonalEntity.class);
    var historyList = List.of(
        this.modelMapper.map(request.getHistoryList(), HistoryEntity[].class));
    // 社員情報の登録（トランザクション）
    this.logic.insert(employee, personal, historyList);
    // 登録した情報を検索して返却
    return this.selectById(employee.getEmployeeId());
  }

  @Override
  public EmployeeDto update(String employeeId, EmployeeRequest request) {
    // 社員が存在する場合のみ後続処理をする
    this.selectById(employeeId);
    // 値の設定
    var employee = this.modelMapper.map(request, EmployeeEntity.class);
    employee.setEmployeeId(employeeId);
    var personal = this.modelMapper.map(request, PersonalEntity.class);
    personal.setEmployeeId(employeeId);
    var historyList = List.of(
        this.modelMapper.map(request.getHistoryList(), HistoryEntity[].class));
    historyList.forEach(history -> history.setEmployeeId(employeeId));
    // 社員情報の更新（トランザクション）
    this.logic.update(employee, personal, historyList);

    return this.selectById(employeeId);
  }

  @Override
  public void deleteById(String employeeId) {
    this.logic.deleteById(employeeId);
  }
}
