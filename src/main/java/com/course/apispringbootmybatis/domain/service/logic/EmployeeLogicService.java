package com.course.apispringbootmybatis.domain.service.logic;

import com.course.apispringbootmybatis.domain.dto.EmployeeDto;
import com.course.apispringbootmybatis.domain.entity.EmployeeEntity;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;

public interface EmployeeLogicService {

  void insert(EmployeeEntity employee, HistoryEntity history);

  EmployeeDto selectById(Integer id);
}
