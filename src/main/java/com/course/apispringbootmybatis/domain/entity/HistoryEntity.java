package com.course.apispringbootmybatis.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryEntity {

  private Integer employeeId;
  private Integer departmentId;
  private String content;


}
