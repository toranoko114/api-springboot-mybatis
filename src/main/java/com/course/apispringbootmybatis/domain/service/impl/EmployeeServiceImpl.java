package com.course.apispringbootmybatis.domain.service.impl;

import com.course.apispringbootmybatis.application.controller.message.EmployeeRequest;
import com.course.apispringbootmybatis.domain.dto.EmployeeDto;
import com.course.apispringbootmybatis.domain.entity.EmployeeEntity;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import com.course.apispringbootmybatis.domain.service.EmployeeService;
import com.course.apispringbootmybatis.domain.service.logic.EmployeeLogicService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final ModelMapper modelMapper;
  private final EmployeeLogicService service;

  @Override
  public EmployeeDto selectById(Integer id) {
    return this.service.selectById(id);
  }

  @Override
  public List<EmployeeDto> selectAll() {
    return List.of(new EmployeeDto());
  }

  @Override
  public EmployeeDto create(EmployeeRequest request) {
    var employee = this.convertReqToEmployeeEntity(request);
    var history = this.convertReqToHistoryEntity(request);
    // 社員情報の登録
    this.service.insert(employee, history);
    // 登録した情報の検索して返却
    return this.selectById(employee.getEmployeeId());
  }

  @Override
  public EmployeeDto update(EmployeeRequest request) {
    return EmployeeDto.builder().build();
  }



  /**
   * リクエストをEmployeeEntityに変換
   *
   * @param request リクエスト
   * @return EmployeeEntity
   */
  private EmployeeEntity convertReqToEmployeeEntity(EmployeeRequest request) {
    return this.modelMapper.map(request, EmployeeEntity.class);
  }

  /**
   * リクエストをHistoryEntityに変換
   *
   * @param request リクエスト
   * @return HistoryEntity
   */
  private HistoryEntity convertReqToHistoryEntity(EmployeeRequest request) {
    return this.modelMapper.map(request, HistoryEntity.class);
  }


}
