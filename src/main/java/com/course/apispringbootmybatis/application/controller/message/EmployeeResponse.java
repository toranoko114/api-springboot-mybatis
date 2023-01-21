package com.course.apispringbootmybatis.application.controller.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
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
  private Department department;
  @JsonProperty("personal")
  private PersonalData personal;

  @JsonProperty("historyList")
  private List<History> historyList;

  @Data
  private static class Department {

    @JsonProperty("departmentId")
    private Integer departmentId;

    @JsonProperty("departmentName")
    private String departmentName;

  }

  @Data
  public static class PersonalData {

    @JsonProperty("employeeId")
    private Integer employeeId;
    @JsonProperty("birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @JsonProperty("telephoneNumber")
    private String telephoneNumber;
    @JsonProperty("mailAddress")
    private String mailAddress;

  }

  @Data
  private static class History {

    @JsonProperty("startDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonProperty("employeeId")
    private Integer employeeId;
    @JsonProperty("departmentId")
    private Integer departmentId;
    @JsonProperty("content")
    private String content;

  }


}
