package com.course.apispringbootmybatis.infrastructure.mapper;

import com.course.apispringbootmybatis.domain.entity.PersonalEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * Personalテーブル Mapperクラス
 */
@Mapper
public interface PersonalMapper {

  /**
   * 社員個人情報の登録・更新.
   *
   * @param personal 社員個人情報
   */
  void upsert(PersonalEntity personal);

  /**
   * 社員IDに該当する社員個人情報の削除.
   *
   * @param employeeId 社員ID
   */
  @Delete("DELETE FROM PERSONAL WHERE EMPLOYEE_ID = #{employeeId}")
  void delete(String employeeId);


}
