package com.course.apispringbootmybatis.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import com.course.apispringbootmybatis.application.exception.EmployeeNotFoundException;
import com.course.apispringbootmybatis.domain.service.impl.EmployeeServiceImpl;
import com.course.apispringbootmybatis.domain.service.logic.EmployeeLogic;
import com.course.apispringbootmybatis.domain.service.logic.impl.EmployeeLogicImpl;
import com.course.apispringbootmybatis.infrastructure.mapper.EmployeeMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DBRider
@MybatisTest
@ActiveProfiles("test")
@Import({EmployeeServiceImpl.class, EmployeeLogicImpl.class})
class EmployeeServiceImplTest {

  @SpyBean
  private EmployeeServiceImpl target;
  @SpyBean
  private EmployeeMapper employeeMapper;
  @MockBean
  private ModelMapper modelMapper;
  @MockBean
  private EmployeeLogic logic;

  @Test
  @DisplayName("正常系-検索結果なし")
  @DataSet("datasets/EmployeeServiceImplTest/input_data.yml")
  void select_no_record() {
    // act
    var actual = catchThrowable(() -> this.target.selectById("test01"));
    // assert
    assertThat(actual).isInstanceOf(EmployeeNotFoundException.class)
        .hasMessage("Employee not found with employeeId:test01");
  }
}
