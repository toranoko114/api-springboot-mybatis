package com.course.apispringbootmybatis.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDto {

  private Integer employeeId;
  private Integer departmentId;
  private String content;

}
