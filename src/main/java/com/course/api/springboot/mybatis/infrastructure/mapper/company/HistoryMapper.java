package com.course.api.springboot.mybatis.infrastructure.mapper.company;

import com.course.api.springboot.mybatis.domain.entity.HistoryEntity;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * Historyテーブル Mapperクラス
 */
@Mapper
public interface HistoryMapper {

  /**
   * 社員履歴情報リストの一括登録・更新.
   *
   * @param historyList 社員履歴情報リスト
   */
  void bulkUpsert(List<HistoryEntity> historyList);

  /**
   * 社員IDに該当する社員履歴情報の削除.
   *
   * @param employeeId 社員ID
   */
  @Delete("DELETE FROM HISTORY WHERE EMPLOYEE_ID = #{employeeId}")
  void delete(String employeeId);

}
