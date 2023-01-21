package com.course.apispringbootmybatis.domain.service.logic.impl;

import com.course.apispringbootmybatis.application.exception.EmployeeNotFoundException;
import com.course.apispringbootmybatis.domain.dto.EmployeeDto;
import com.course.apispringbootmybatis.domain.entity.EmployeeEntity;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import com.course.apispringbootmybatis.domain.entity.PersonalDataEntity;
import com.course.apispringbootmybatis.domain.service.logic.EmployeeLogic;
import com.course.apispringbootmybatis.infrastructure.mapper.EmployeeMapper;
import com.course.apispringbootmybatis.infrastructure.mapper.HistoryMapper;
import com.course.apispringbootmybatis.infrastructure.mapper.PersonalDataMapper;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 社員情報APIのロジック実装クラス
 */
@Service
@AllArgsConstructor
public class EmployeeLogicImpl implements EmployeeLogic {

  private final EmployeeMapper employeeMapper;
  private final HistoryMapper historyMapper;

  private final PersonalDataMapper personalDataMapper;

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
   * @param employee    社員
   * @param personal    個人情報
   * @param historyList 履歴リスト
   */
  @Override
  @Transactional
  public void insert(EmployeeEntity employee, PersonalDataEntity personal,
      List<HistoryEntity> historyList) {
    //社員テーブルに追加
    employeeMapper.insert(employee);
    // 個人情報テーブルに追加
    personal.setEmployeeId(employee.getEmployeeId());
    personalDataMapper.upsert(personal);
    //履歴テーブルに追加
    historyList.forEach(history -> history.setEmployeeId(employee.getEmployeeId()));
    historyMapper.bulkUpsert(historyList);
  }

  @Override
  @Transactional
  public void update(EmployeeEntity employee, PersonalDataEntity personal,
      List<HistoryEntity> historyList) {
    //社員テーブルに追加
    employeeMapper.update(employee);
    // 個人情報テーブルに追加
    personalDataMapper.upsert(personal);
    //履歴テーブルに追加
    historyMapper.bulkUpsert(historyList);
  }

  @Override
  @Transactional
  public void deleteById(Integer employeeId) {
    employeeMapper.delete(employeeId);
    personalDataMapper.delete(employeeId);
    historyMapper.delete(employeeId);
  }
}
