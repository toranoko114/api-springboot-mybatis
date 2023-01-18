package com.course.apispringbootmybatis.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DepartmentEnum {

  OTHER(0, "その他"),
  INDEPENDENT(1, "無所属"),
  SALES(2, "営業部"),
  PLANNING(3, "企画部"),
  DEVELOPMENT(4, "開発部");


  // 部署ID
  private final Integer id;

  // 部署名
  private final String departmentName;

}
