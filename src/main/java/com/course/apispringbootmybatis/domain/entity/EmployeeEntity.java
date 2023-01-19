package com.course.apispringbootmybatis.domain.entity;

import com.course.apispringbootmybatis.enums.DepartmentEnum;
import com.course.apispringbootmybatis.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {

  private Integer employeeId;
  private String gender;
  private Integer departmentId;
  private String employeeName;


}
