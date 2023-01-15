package com.course.apispringbootmybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {

  private int id;
  private String Name;
  private Department department;



}
