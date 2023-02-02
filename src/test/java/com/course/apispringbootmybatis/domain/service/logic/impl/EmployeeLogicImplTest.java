package com.course.apispringbootmybatis.domain.service.logic.impl;

import com.course.apispringbootmybatis.domain.dto.EmployeeDto;
import com.course.apispringbootmybatis.domain.dto.HistoryDto;
import com.course.apispringbootmybatis.domain.dto.PersonalDto;
import com.course.apispringbootmybatis.domain.entity.EmployeeEntity;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import com.course.apispringbootmybatis.domain.entity.PersonalEntity;
import com.course.apispringbootmybatis.enums.Department;
import com.course.apispringbootmybatis.enums.Gender;
import com.course.apispringbootmybatis.infrastructure.mapper.EmployeeMapper;
import com.course.apispringbootmybatis.infrastructure.mapper.HistoryMapper;
import com.course.apispringbootmybatis.infrastructure.mapper.PersonalMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@DBRider
@MybatisTest
@ActiveProfiles("test")
class EmployeeLogicImplTest {

  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  @DisplayName("トランザクション-正常系")
  class insert {

    private EmployeeLogicImpl target;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private PersonalMapper personalMapper;

    @BeforeEach
    public void beforeEach() {
      target = new EmployeeLogicImpl(employeeMapper, historyMapper, personalMapper);
    }

    @Test
    @DisplayName("正常系")
    @DataSet("datasets/EmployeeLogicImplTest/input_data.yml")
    void insert_transaction_normal() {
      // arrange
      var employee = EmployeeEntity.builder().employeeId("test2").employeeName("テスト2")
          .departmentId(4).gender("MALE").build();

      var personal = PersonalEntity.builder().employeeId("test2").birthday(LocalDate.of(2020, 2, 2))
          .telephoneNumber("00000000002").mailAddress("test2@gmail.com").build();

      var history = List.of(
          HistoryEntity.builder().employeeId("test2").startDate(LocalDate.of(2022, 2, 2))
              .departmentId(4).content("テスト2入社").build());

      var expectedEmployee1 = EmployeeDto.builder()
          .employeeId("test")
          .department(Department.INDEPENDENT)
          .employeeName("テスト")
          .gender(Gender.FEMALE)
          .personal(PersonalDto.builder()
              .employeeId("test")
              .birthday(LocalDate.of(2020, 1, 1))
              .telephoneNumber("00000000001")
              .mailAddress("test@gmail.com")
              .build())
          .historyList(List.of(
              HistoryDto.builder()
                  .employeeId("test")
                  .startDate(LocalDate.of(2022, 1, 1))
                  .departmentId(2)
                  .content("テスト入社")
                  .build(),
              HistoryDto.builder()
                  .employeeId("test")
                  .startDate(LocalDate.of(2023, 1, 1))
                  .departmentId(1)
                  .content("テスト退職")
                  .build()
          )).build();

      var expectedEmployee2 = EmployeeDto.builder()
          .employeeId("test2")
          .department(Department.DEVELOPMENT)
          .employeeName("テスト2")
          .gender(Gender.MALE)
          .personal(PersonalDto.builder()
              .employeeId("test2")
              .birthday(LocalDate.of(2020, 2, 2))
              .telephoneNumber("00000000002")
              .mailAddress("test2@gmail.com")
              .build()
          )
          .historyList(List.of(
              HistoryDto.builder()
                  .employeeId("test2")
                  .startDate(LocalDate.of(2022, 2, 2))
                  .departmentId(4)
                  .content("テスト2入社")
                  .build()
          )).build();

      // act
      target.insert(employee, personal, history);
      // assert
      var actual = employeeMapper.selectAll();
      var expected = List.of(expectedEmployee1, expectedEmployee2);
      Assertions.assertEquals(expected, actual);
    }
  }
}
