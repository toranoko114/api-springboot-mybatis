package com.course.apispringbootmybatis.infrastructure.mapper;

import com.course.apispringbootmybatis.domain.dto.PersonalDataDto;
import com.course.apispringbootmybatis.domain.entity.PersonalDataEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * PersonalDataテーブル Mapperクラス
 */
@Mapper
public interface PersonalDataMapper {

  /**
   * 社員IDに該当する社員個人情報の取得.
   *
   * @param employeeId 社員ID
   * @return 社員個人情報
   */
  @Select("SELECT * FROM HISTORY "
      + "WHERE EMPLOYEE_ID = #{employeeId}")
  List<PersonalDataDto> selectById(Integer employeeId);

  /**
   * 社員個人情報の登録・更新.
   *
   * @param personal 社員個人情報
   */
  void upsert(PersonalDataEntity personal);


}
