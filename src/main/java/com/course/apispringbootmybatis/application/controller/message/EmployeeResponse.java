package com.course.apispringbootmybatis.application.controller.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

  @JsonProperty("employeeId")
  private String employeeId;
  @JsonProperty("employeeName")
  private String employeeName;
  @JsonProperty("gender")
  private String gender;
  @JsonProperty("department")
  private DepartmentDto department;

  @JsonProperty("historyList")
  private List<HistoryDto> historyList;

  @Data
  private static class DepartmentDto {

    @JsonProperty("departmentId")
    private Integer departmentId;

    @JsonProperty("departmentName")
    private String departmentName;

  }

  @Data
  private static class HistoryDto {

    @JsonProperty("employeeId")
    private Integer employeeId;
    @JsonProperty("departmentId")
    private Integer departmentId;
    @JsonProperty("content")
    private String content;

  }


}
