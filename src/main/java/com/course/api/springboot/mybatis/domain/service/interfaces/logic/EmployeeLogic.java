package com.course.api.springboot.mybatis.domain.service.interfaces.logic;

import com.course.api.springboot.mybatis.domain.entity.EmployeeEntity;
import com.course.api.springboot.mybatis.domain.entity.HistoryEntity;
import com.course.api.springboot.mybatis.domain.entity.PersonalEntity;
import java.util.List;

/**
 * 社員情報APIのロジックインターフェース
 */
public interface EmployeeLogic {

  /**
   * 社員の各種情報の登録・更新.
   *
   * @param employee 社員情報
   * @param personal 社員個人情報
   * @param history  社員履歴情報
   */
  void upsert(EmployeeEntity employee, PersonalEntity personal, List<HistoryEntity> history);

  /**
   * 社員IDに該当する社員情報の削除.
   *
   * @param employeeId 社員ID
   */
  void deleteById(String employeeId);

}
