package com.course.apispringbootmybatis.infrastructure.mapper;

import com.course.apispringbootmybatis.domain.dto.HistoryDto;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Historyテーブル Mapperクラス
 */
@Mapper
public interface HistoryMapper {

  /**
   * 社員IDに該当する社員履歴情報リストの取得.
   *
   * @param employeeId 社員ID
   * @return 社員履歴情報リスト
   */
  @Select("SELECT * WHERE EMPLOYEE_ID = #{employeeId}")
  List<HistoryDto> selectById(Integer employeeId);

  /**
   * 社員履歴情報リストの一括登録・更新.
   *
   * @param historyList 社員履歴情報リスト
   */
  void bulkUpsert(List<HistoryEntity> historyList);

}
