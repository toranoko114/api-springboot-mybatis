package com.course.apispringbootmybatis.domain.service;

import com.course.apispringbootmybatis.application.controller.message.EmployeeRequest;
import com.course.apispringbootmybatis.domain.dto.EmployeeDto;
import java.util.List;

/**
 * 社員情報APIのサービスインターフェース
 */
public interface EmployeeService {

  /**
   * 社員IDに該当する社員情報の取得.
   *
   * @param employeeId 社員ID
   * @return 社員情報
   */
  EmployeeDto selectById(Integer employeeId);

  /**
   * 全ての社員情報の取得.
   *
   * @return 社員情報リスト
   */
  List<EmployeeDto> selectAll();

  /**
   * 社員情報の登録.
   *
   * @param request リクエスト
   * @return EmployeeDto 社員情報Dto
   */
  EmployeeDto create(EmployeeRequest request);

  /**
   * 社員情報の更新.
   *
   * @param employeeId リクエスト
   * @param request    社員情報Dto
   * @return EmployeeDto
   */
  EmployeeDto update(Integer employeeId, EmployeeRequest request);

  /**
   * 社員IDに該当する社員情報の削除.
   *
   * @param employeeId 社員ID
   */
  void deleteById(Integer employeeId);


}
