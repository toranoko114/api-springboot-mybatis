package com.course.apispringbootmybatis.domain.dto;

import com.course.apispringbootmybatis.enums.DepartmentEnum;
import com.course.apispringbootmybatis.enums.GenderEnum;
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
  private GenderEnum gender;
  private DepartmentEnum department;
  private List<HistoryDto> historyList;

}
