package com.course.apispringbootmybatis.entity;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public enum DepartmentEnum {

  SALES(1,"営業部"),
  PLANNING(2, "企画部"),
  DEVELOPMENT(3, "開発部");

  // 部署ID
  private int id;

  // 部署名
  private String departmentName;

}
