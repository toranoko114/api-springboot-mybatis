package com.course.apispringbootmybatis.application.controller.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

  @NotNull
  @JsonProperty("employeeName")
  private String employeeName;
  @NotNull
  @JsonProperty("gender")
  private String gender;
  @NotNull
  @JsonProperty("departmentId")
  private Integer departmentId;
  @JsonProperty("content")
  private String content;


}
