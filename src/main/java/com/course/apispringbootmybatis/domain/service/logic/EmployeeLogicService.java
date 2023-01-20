package com.course.apispringbootmybatis.domain.service.logic;

import com.course.apispringbootmybatis.domain.dto.EmployeeDto;
import com.course.apispringbootmybatis.domain.entity.EmployeeEntity;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import com.course.apispringbootmybatis.domain.entity.PersonalDataEntity;

public interface EmployeeLogicService {

  EmployeeDto selectById(Integer id);

  void insert(EmployeeEntity employee, PersonalDataEntity personal, HistoryEntity history);

}
