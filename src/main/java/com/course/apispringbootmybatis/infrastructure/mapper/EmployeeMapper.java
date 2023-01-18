package com.course.apispringbootmybatis.infrastructure.mapper;

import com.course.apispringbootmybatis.domain.dto.EmployeeDto;
import com.course.apispringbootmybatis.domain.entity.EmployeeEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmployeeMapper {

  @Insert("INSERT INTO EMPLOYEE (DEPARTMENT_ID, EMPLOYEE_NAME) VALUES (#{departmentId}, #{employeeName})")
  @Options(useGeneratedKeys = true, keyProperty = "employeeId")
  void insert(EmployeeEntity employee);

  EmployeeDto selectById(@Param("employeeId") Integer employeeId);

}
