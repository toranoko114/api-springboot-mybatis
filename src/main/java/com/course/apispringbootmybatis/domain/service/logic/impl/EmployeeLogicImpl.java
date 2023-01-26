package com.course.apispringbootmybatis.domain.service.logic.impl;

import com.course.apispringbootmybatis.domain.entity.EmployeeEntity;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import com.course.apispringbootmybatis.domain.entity.PersonalDataEntity;
import com.course.apispringbootmybatis.domain.service.logic.EmployeeLogic;
import com.course.apispringbootmybatis.infrastructure.mapper.EmployeeMapper;
import com.course.apispringbootmybatis.infrastructure.mapper.HistoryMapper;
import com.course.apispringbootmybatis.infrastructure.mapper.PersonalMapper;
import java.util.List;
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

  private final PersonalMapper personalMapper;

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
    employeeMapper.upsert(employee);
    // 個人情報テーブルに追加
    personalMapper.upsert(personal);
    //履歴テーブルに追加
    historyList.forEach(history -> history.setEmployeeId(employee.getEmployeeId()));
    historyMapper.bulkUpsert(historyList);
  }

  @Override
  @Transactional
  public void update(EmployeeEntity employee, PersonalDataEntity personal,
      List<HistoryEntity> historyList) {
    //社員テーブルに追加
    employeeMapper.upsert(employee);
    // 個人情報テーブルに追加
    personalMapper.upsert(personal);
    //履歴テーブルに追加
    historyMapper.bulkUpsert(historyList);
  }

  @Override
  @Transactional
  public void deleteById(String employeeId) {
    employeeMapper.delete(employeeId);
    personalMapper.delete(employeeId);
    historyMapper.delete(employeeId);
  }
}
