package com.course.apispringbootmybatis.domain.service.logic;

import com.course.apispringbootmybatis.domain.entity.EmployeeEntity;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import com.course.apispringbootmybatis.domain.entity.PersonalDataEntity;
import java.util.List;

/**
 * 社員情報APIのロジックインターフェース
 */
public interface EmployeeLogic {

  /**
   * 社員の各種情報の登録.
   *
   * @param employee 社員情報
   * @param personal 社員個人情報
   * @param history  社員履歴情報
   */
  void insert(EmployeeEntity employee, PersonalDataEntity personal, List<HistoryEntity> history);

  /**
   * 社員の各種情報の更新.
   *
   * @param employee 社員情報
   * @param personal 社員個人情報
   * @param history  社員履歴情報
   */
  void update(EmployeeEntity employee, PersonalDataEntity personal, List<HistoryEntity> history);

  /**
   * 社員IDに該当する社員情報の削除.
   *
   * @param employeeId 社員ID
   */
  void deleteById(String employeeId);

}
