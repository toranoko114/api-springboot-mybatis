package com.course.apispringbootmybatis.domain.dto;

import com.course.apispringbootmybatis.enums.Department;
import com.course.apispringbootmybatis.enums.Gender;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

  private Integer employeeId;
  private String employeeName;
  private Gender gender;
  private Department department;
  private PersonalDataDto personal;
  private List<HistoryDto> historyList;

}
