package com.course.api.springboot.mybatis.domain.service.company;

import com.course.api.springboot.mybatis.application.controller.message.EmployeeRequest;
import com.course.api.springboot.mybatis.application.exception.EmployeeNotFoundException;
import com.course.api.springboot.mybatis.domain.service.interfaces.EmployeeService;
import com.course.api.springboot.mybatis.domain.service.interfaces.logic.EmployeeLogic;
import com.course.api.springboot.mybatis.domain.dto.EmployeeDto;
import com.course.api.springboot.mybatis.domain.entity.EmployeeEntity;
import com.course.api.springboot.mybatis.domain.entity.HistoryEntity;
import com.course.api.springboot.mybatis.domain.entity.PersonalEntity;
import com.course.api.springboot.mybatis.infrastructure.mapper.company.EmployeeMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 社員情報APIのサービス実装クラス
 */
@Service("companyEmployeeServiceImpl")
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  @Qualifier("companyEmployeeLogicImpl") private final EmployeeLogic logic;
  private final ModelMapper modelMapper;
  private final EmployeeMapper employeeMapper;

  @Override
  public EmployeeDto selectById(String employeeId) {
    return this.employeeMapper.selectById(employeeId)
        .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
  }

  @Override
  public List<EmployeeDto> selectAll() {
    return this.employeeMapper.selectAll();
  }

  @Override
  public EmployeeDto create(EmployeeRequest request) {

    // Entity変換
    var employee = this.modelMapper.map(request, EmployeeEntity.class);
    var personal = this.modelMapper.map(request, PersonalEntity.class);
    var historyList = List.of(
        this.modelMapper.map(request.getHistoryList(), HistoryEntity[].class));
    historyList.forEach(history -> history.setEmployeeId(request.getEmployeeId()));

    // 社員情報の登録（トランザクション）
    this.logic.upsert(employee, personal, historyList);

    // 登録した情報を検索して返却
    return this.selectById(employee.getEmployeeId());
  }

  @Override
  public EmployeeDto update(String employeeId, EmployeeRequest request) {

    // 社員が存在する場合のみ後続処理をする
    this.selectById(employeeId);

    // Entity変換
    var employee = this.modelMapper.map(request, EmployeeEntity.class);
    employee.setEmployeeId(employeeId);
    var personal = this.modelMapper.map(request, PersonalEntity.class);
    personal.setEmployeeId(employeeId);
    var historyList = List.of(
        this.modelMapper.map(request.getHistoryList(), HistoryEntity[].class));
    historyList.forEach(history -> history.setEmployeeId(employeeId));

    // 社員情報の更新（トランザクション）
    this.logic.upsert(employee, personal, historyList);

    // 更新した情報を検索して返却
    return this.selectById(employeeId);
  }

  @Override
  public void deleteById(String employeeId) {
    // 社員が存在する場合のみ後続処理をする
    this.selectById(employeeId);
    this.logic.deleteById(employeeId);
  }
}
