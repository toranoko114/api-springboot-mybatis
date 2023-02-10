package com.course.api.springboot.mybatis.domain.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDto {

  private String employeeId;
  private LocalDate birthday;
  private String telephoneNumber;
  private String mailAddress;

}
