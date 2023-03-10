package com.course.api.springboot.mybatis.infrastructure.mapper.company;

import com.course.api.springboot.mybatis.domain.dto.EmployeeDto;
import com.course.api.springboot.mybatis.domain.entity.EmployeeEntity;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Employeeテーブル Mapperクラス
 */
@Mapper
public interface EmployeeMapper {

  /**
   * 社員IDに該当する社員情報の取得.
   *
   * @param employeeId 社員ID
   * @return 社員情報
   */
  Optional<EmployeeDto> selectById(@Param("employeeId") String employeeId);

  /**
   * 全ての社員情報の取得.
   *
   * @return 社員情報リスト
   */
  List<EmployeeDto> selectAll();

  /**
   * 社員情報の登録または更新.
   *
   * @param employee 社員情報エンティティ
   */
  void upsert(EmployeeEntity employee);

  /**
   * 社員IDに該当する社員情報の削除.
   *
   * @param employeeId 社員ID
   */
  @Delete("DELETE FROM EMPLOYEE WHERE EMPLOYEE_ID = #{employeeId}")
  void delete(String employeeId);

}
