package com.course.apispringbootmybatis.domain.service.logic.impl;

import com.course.apispringbootmybatis.application.exception.EmployeeNotFoundException;
import com.course.apispringbootmybatis.domain.dto.EmployeeDto;
import com.course.apispringbootmybatis.domain.entity.EmployeeEntity;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import com.course.apispringbootmybatis.domain.service.logic.EmployeeLogicService;
import com.course.apispringbootmybatis.infrastructure.mapper.EmployeeMapper;
import com.course.apispringbootmybatis.infrastructure.mapper.HistoryMapper;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EmployeeLogicServiceImpl implements EmployeeLogicService {

  private final EmployeeMapper employeeMapper;
  private final HistoryMapper historyMapper;

  @Override
  public EmployeeDto selectById(Integer employeeId) {
    // 社員情報がない場合はExceptionをthrowする
    var employeeDto = this.employeeMapper.selectById(employeeId)
        .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    // 社員情報がある場合のみ履歴を取得する
    employeeDto.setHistoryList(Optional.of(historyMapper.selectById(employeeId)).orElse(List.of()));
    return employeeDto;
  }

  /**
   * 社員情報を登録する.
   *
   * @param employee 社員
   * @param history  履歴
   */
  @Override
  @Transactional
  public void insert(EmployeeEntity employee, HistoryEntity history) {
    //社員テーブルに追加
    employeeMapper.insert(employee);
    //履歴テーブルに追加
    history.setEmployeeId(employee.getEmployeeId());
    historyMapper.insert(history);
  }

}
