package com.course.apispringbootmybatis.infrastructure.mapper.sub_company;

import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * 子会社 Historyテーブル Mapperクラス
 */
@Mapper
public interface SubHistoryMapper {

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
