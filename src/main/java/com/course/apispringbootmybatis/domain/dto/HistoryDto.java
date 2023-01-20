package com.course.apispringbootmybatis.domain.dto;

import java.util.Date;
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
  private Integer employeeId;
  private Integer departmentId;
  private Date startDate;
  private String content;

}
