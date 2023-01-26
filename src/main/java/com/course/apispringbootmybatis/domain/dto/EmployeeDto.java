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

  private String employeeId;
  private String employeeName;
  private Gender gender;
  private Department department;
  private PersonalDto personal;
  private List<HistoryDto> historyList;

}
