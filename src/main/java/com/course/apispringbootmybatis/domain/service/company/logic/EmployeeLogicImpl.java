package com.course.apispringbootmybatis.domain.service.company.logic;

import com.course.apispringbootmybatis.domain.entity.EmployeeEntity;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import com.course.apispringbootmybatis.domain.entity.PersonalEntity;
import com.course.apispringbootmybatis.domain.service.interfaces.logic.EmployeeLogic;
import com.course.apispringbootmybatis.infrastructure.mapper.company.EmployeeMapper;
import com.course.apispringbootmybatis.infrastructure.mapper.company.HistoryMapper;
import com.course.apispringbootmybatis.infrastructure.mapper.company.PersonalMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 社員情報APIのロジック実装クラス
 */
@Service("companyEmployeeLogicImpl")
@AllArgsConstructor
public class EmployeeLogicImpl implements EmployeeLogic {

  private final EmployeeMapper employeeMapper;
  private final HistoryMapper historyMapper;
  private final PersonalMapper personalMapper;

  /**
   * 登録更新トランザクション（新規トランザクションを生成する）
   *
   * @param employee    社員情報
   * @param personal    社員個人情報
   * @param historyList 社員履歴情報
   * @see "https://spring.pleiades.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Propagation.html"
   */
  @Override
  @Transactional(transactionManager = "companyDataSourceTransactionManager", propagation = Propagation.NESTED)
  public void upsert(EmployeeEntity employee, PersonalEntity personal,
      List<HistoryEntity> historyList) {
    employeeMapper.upsert(employee);
    personalMapper.upsert(personal);
    historyMapper.bulkUpsert(historyList);
  }

  /**
   * 削除トランザクション（新規トランザクションを生成する）
   *
   * @param employeeId 社員ID
   * @see "https://spring.pleiades.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Propagation.html"
   */
  @Override
  @Transactional(transactionManager = "companyDataSourceTransactionManager", propagation = Propagation.NESTED)
  public void deleteById(String employeeId) {
    employeeMapper.delete(employeeId);
    personalMapper.delete(employeeId);
    historyMapper.delete(employeeId);
  }
}
