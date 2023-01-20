package com.course.apispringbootmybatis.application.controller.message;


import com.course.apispringbootmybatis.application.controller.validation.RegexConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
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
  private Date birthday;
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
  @JsonProperty("content")
  private String content;


}
