package com.course.apispringbootmybatis.domain.entity;

import java.util.Date;
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
  private Date startDate;
  private Integer employeeId;
  private Integer departmentId;
  private String content;


}
