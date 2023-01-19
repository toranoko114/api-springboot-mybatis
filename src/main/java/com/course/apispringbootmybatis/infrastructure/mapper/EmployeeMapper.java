package com.course.apispringbootmybatis.infrastructure.mapper;

import com.course.apispringbootmybatis.domain.dto.EmployeeDto;
import com.course.apispringbootmybatis.domain.entity.EmployeeEntity;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmployeeMapper {

  Optional<EmployeeDto> selectById(@Param("employeeId") Integer employeeId);

  List<EmployeeDto> selectAll();

  @Insert("INSERT INTO EMPLOYEE (DEPARTMENT_ID, EMPLOYEE_NAME, GENDER) VALUES (#{departmentId}, #{employeeName}, #{gender})")
  @Options(useGeneratedKeys = true, keyProperty = "employeeId")
  void insert(EmployeeEntity employee);
}
