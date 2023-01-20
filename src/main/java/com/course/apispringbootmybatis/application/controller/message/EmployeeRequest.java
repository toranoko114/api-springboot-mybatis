package com.course.apispringbootmybatis.application.controller.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
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
  @NotNull
  @JsonProperty("birthday")
  private Date birthday;
  @NotNull
  @JsonProperty("telephoneNumber")
  private String telephoneNumber;
  @NotNull
  @JsonProperty("mailAddress")
  private String mailAddress;
  @JsonProperty("content")
  private String content;


}
