package com.course.apispringbootmybatis.domain.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDataDto {

  private Integer employeeId;
  private Date birthday;
  private String telephoneNumber;
  private String mailAddress;

}
