package com.course.apispringbootmybatis.domain.dto;

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
  private DepartmentDto department;
  private List<HistoryDto> historyList;

}
