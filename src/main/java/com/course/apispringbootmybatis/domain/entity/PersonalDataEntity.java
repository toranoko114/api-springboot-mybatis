package com.course.apispringbootmybatis.domain.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDataEntity {

  private Integer employeeId;
  private LocalDate birthday;
  private String telephoneNumber;
  private String mailAddress;


}
