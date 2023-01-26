package com.course.apispringbootmybatis.infrastructure.mapper;

import com.course.apispringbootmybatis.domain.dto.EmployeeDto;
import com.course.apispringbootmybatis.domain.entity.EmployeeEntity;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
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
   * 社員情報の登録.
   *
   * @param employee 社員情報エンティティ
   */
  @Insert("INSERT INTO EMPLOYEE "
      + "(DEPARTMENT_ID, EMPLOYEE_NAME, GENDER) "
      + "VALUES (#{departmentId}, #{employeeName}, #{gender})")
  @Options(useGeneratedKeys = true, keyProperty = "employeeId")
  void insert(EmployeeEntity employee);

  /**
   * 社員IDに該当する社員情報の更新.
   *
   * @param employee 社員情報エンティティ
   */
  void update(EmployeeEntity employee);

  /**
   * 社員IDに該当する社員情報の削除.
   *
   * @param employeeId 社員ID
   */
  @Delete("DELETE FROM EMPLOYEE WHERE EMPLOYEE_ID = #{employeeId}")
  void delete(String employeeId);

}
