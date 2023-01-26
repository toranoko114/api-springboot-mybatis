package com.course.apispringbootmybatis.domain.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDto {

  private Integer historyId;
  private String employeeId;
  private Integer departmentId;
  private LocalDate startDate;
  private String content;

}
