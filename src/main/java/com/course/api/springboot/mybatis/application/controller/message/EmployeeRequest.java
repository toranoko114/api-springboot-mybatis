package com.course.api.springboot.mybatis.application.controller.message;


import com.course.api.springboot.mybatis.application.controller.validation.RegexConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
  @JsonProperty("employeeId")
  private String employeeId;
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
  @JsonFormat(pattern = "yyyy-MM-dd")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate birthday;
  @NotNull
  @JsonProperty("telephoneNumber")
  @Pattern(regexp = RegexConstants.TELPHONE_NUMBER, message = "電話番号の形式が違います。")
  @Size(max = 20)
  private String telephoneNumber;
  @NotNull
  @JsonProperty("mailAddress")
  @Pattern(regexp = RegexConstants.MAIL_ADDRESS, message = "メールアドレスの形式が違います。")
  @Size(max = 256)
  private String mailAddress;
  @JsonProperty("historyList")
  private List<History> historyList;

  @Data
  private static class History {

    @NotNull
    @JsonProperty("startDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;
    @NotNull
    @JsonProperty("departmentId")
    private Integer departmentId;
    @JsonProperty("content")
    private String content;

  }


}
