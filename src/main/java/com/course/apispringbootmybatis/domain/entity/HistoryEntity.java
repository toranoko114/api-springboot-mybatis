package com.course.apispringbootmybatis.domain.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryEntity {

  private Integer historyId;
  private LocalDate startDate;
  private String employeeId;
  private Integer departmentId;
  private String content;


}
