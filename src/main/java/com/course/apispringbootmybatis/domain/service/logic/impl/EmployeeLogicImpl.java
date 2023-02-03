package com.course.apispringbootmybatis.domain.service.logic.impl;

import com.course.apispringbootmybatis.domain.entity.EmployeeEntity;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import com.course.apispringbootmybatis.domain.entity.PersonalEntity;
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

  @Override
  @Transactional
  public void upsert(EmployeeEntity employee, PersonalEntity personal,
      List<HistoryEntity> historyList) {
    employeeMapper.upsert(employee);
    personalMapper.upsert(personal);
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
