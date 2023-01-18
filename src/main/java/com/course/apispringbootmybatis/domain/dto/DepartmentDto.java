package com.course.apispringbootmybatis.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

  // 部署ID
  private Integer departmentId;

  // 部署名
  private String departmentName;

}
