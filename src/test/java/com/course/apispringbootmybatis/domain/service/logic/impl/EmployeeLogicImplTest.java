package com.course.apispringbootmybatis.domain.service.logic.impl;

import static org.mockito.ArgumentMatchers.any;

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
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

@DBRider
@MybatisTest
@ActiveProfiles("test")
@ExtendWith(SoftAssertionsExtension.class)
class EmployeeLogicImplTest {

  @InjectSoftAssertions
  private SoftAssertions softy;

  /**
   * クラス共通で利用するEntity
   */
  EmployeeEntity employee = EmployeeEntity.builder().employeeId("test2").employeeName("テスト2")
      .departmentId(4).gender("MALE").build();

  PersonalEntity personal = PersonalEntity.builder().employeeId("test2")
      .birthday(LocalDate.of(2020, 2, 2))
      .telephoneNumber("00000000002").mailAddress("test2@gmail.com").build();

  List<HistoryEntity> history = List.of(
      HistoryEntity.builder().employeeId("test2").startDate(LocalDate.of(2022, 2, 2))
          .departmentId(4).content("テスト2入社").build());


  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  @DisplayName("トランザクション-正常系")
  class transactionNormal {

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
    @DisplayName("トランザクション-正常系-登録")
    @DataSet(value = "datasets/EmployeeLogicImplTest/input_data.yml", cleanBefore = true)
    void insert() {

      var expectedEmployee1 = EmployeeDto.builder().employeeId("test")
          .department(Department.INDEPENDENT).employeeName("テスト").gender(Gender.FEMALE).personal(
              PersonalDto.builder().employeeId("test").birthday(LocalDate.of(2020, 1, 1))
                  .telephoneNumber("00000000001").mailAddress("test@gmail.com").build())
          .historyList(List.of(
              HistoryDto.builder().employeeId("test").startDate(LocalDate.of(2022, 1, 1))
                  .departmentId(2).content("テスト入社").build(),
              HistoryDto.builder().employeeId("test").startDate(LocalDate.of(2023, 1, 1))
                  .departmentId(1).content("テスト退職").build())).build();

      var expectedEmployee2 = EmployeeDto.builder().employeeId("test2")
          .department(Department.DEVELOPMENT).employeeName("テスト2").gender(Gender.MALE).personal(
              PersonalDto.builder().employeeId("test2").birthday(LocalDate.of(2020, 2, 2))
                  .telephoneNumber("00000000002").mailAddress("test2@gmail.com").build())
          .historyList(List.of(
              HistoryDto.builder().employeeId("test2").startDate(LocalDate.of(2022, 2, 2))
                  .departmentId(4).content("テスト2入社").build())).build();

      // act
      target.upsert(employee, personal, history);
      // assert
      var actual = employeeMapper.selectAll();
      var expected = List.of(expectedEmployee1, expectedEmployee2);
      softy.assertThat(actual).as("transactionNormal_insert: database compare").isEqualTo(expected);
    }

    @Test
    @DisplayName("トランザクション-正常系-更新")
    @DataSet(value = "datasets/EmployeeLogicImplTest/input_data.yml", cleanBefore = true)
    void update() {
      // arrange
      var employee = EmployeeEntity.builder().employeeId("test").employeeName("テスト更新")
          .departmentId(3).gender("MALE").build();

      var personal = PersonalEntity.builder().employeeId("test").birthday(LocalDate.of(2020, 1, 1))
          .telephoneNumber("00000000002").mailAddress("test-update@gmail.com").build();

      var history = List.of(
          HistoryEntity.builder().employeeId("test").startDate(LocalDate.of(2022, 1, 1))
              .departmentId(2).content("テスト入社").build(),
          HistoryEntity.builder().employeeId("test").startDate(LocalDate.of(2023, 1, 1))
              .departmentId(1).content("テスト退職-更新").build(),
          HistoryEntity.builder().employeeId("test").startDate(LocalDate.of(2024, 1, 1))
              .departmentId(3).content("テスト退職-追加").build());

      var expectedEmployee = EmployeeDto.builder().employeeId("test")
          .department(Department.PLANNING).employeeName("テスト更新").gender(Gender.MALE).personal(
              PersonalDto.builder().employeeId("test").birthday(LocalDate.of(2020, 1, 1))
                  .telephoneNumber("00000000002").mailAddress("test-update@gmail.com").build())
          .historyList(List.of(
              HistoryDto.builder().employeeId("test").startDate(LocalDate.of(2022, 1, 1))
                  .departmentId(2).content("テスト入社").build(),
              HistoryDto.builder().employeeId("test").startDate(LocalDate.of(2023, 1, 1))
                  .departmentId(1).content("テスト退職-更新").build(),
              HistoryDto.builder().employeeId("test").startDate(LocalDate.of(2024, 1, 1))
                  .departmentId(3).content("テスト退職-追加").build())).build();
      // act
      target.upsert(employee, personal, history);
      // assert
      var actual = employeeMapper.selectAll();
      var expected = List.of(expectedEmployee);
      softy.assertThat(actual).as("transactionNormal_update: database compare").isEqualTo(expected);
    }

    @Test
    @DisplayName("トランザクション-正常系-削除")
    @DataSet(value = "datasets/EmployeeLogicImplTest/input_data.yml", cleanBefore = true)
    void delete() {
      // act
      target.deleteById("test");
      // assert
      var actual = employeeMapper.selectAll();
      var expected = List.of();
      softy.assertThat(actual).as("transactionNormal_delete: database compare").isEqualTo(expected);
    }
  }

  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  @DisplayName("トランザクション-異常系")
  class transactionAbnormal {

    @SpyBean
    private EmployeeLogicImpl target;
    @SpyBean
    private EmployeeMapper employeeMapper;
    @SpyBean
    private PersonalMapper personalMapper;
    @SpyBean
    private HistoryMapper historyMapper;

    @Test
    @DisplayName("トランザクション-異常系-Employee登録時にエラー")
    @DataSet(value = "datasets/EmployeeLogicImplTest/input_data.yml", cleanBefore = true)
    void upsert_employeeMapper_Error() {

      // EmployeeMapper更新処理が呼ばれたら例外が投げられるよう設定
      Mockito.doThrow(new RuntimeException()).when(employeeMapper).upsert(employee);

      var expected = employeeMapper.selectAll();

      // act
      try {
        target.upsert(employee, personal, history);
      } catch (RuntimeException e) {
        // テストのために素通りさせる
      }
      // assert
      var actual = employeeMapper.selectAll();

      softy.assertThatCode(
          () -> Mockito.verify(this.employeeMapper, Mockito.times(1)).upsert(any())
      ).as("EmployeeMapper#upsert() is called").doesNotThrowAnyException();
      softy.assertThatCode(
          () -> Mockito.verify(this.personalMapper, Mockito.times(0)).upsert(any())
      ).as("PersonalMapper#upsert() is called").doesNotThrowAnyException();
      softy.assertThatCode(
          () -> Mockito.verify(this.historyMapper, Mockito.times(0)).bulkUpsert(any())
      ).as("HistoryMapper#upsert() is called").doesNotThrowAnyException();
      softy.assertThat(actual).as("upsert_employeeMapper_Error: database compare")
          .isEqualTo(expected);
    }

    @Test
    @DisplayName("トランザクション-異常系-Personal登録時にエラー")
    @DataSet(value = "datasets/EmployeeLogicImplTest/input_data.yml", cleanBefore = true)
    void upsert_personalMapper_Error() {

      // EmployeeMapper更新処理が呼ばれたら例外が投げられるよう設定
      Mockito.doThrow(new NullPointerException()).when(personalMapper).upsert(personal);

      var expected = employeeMapper.selectAll();

      // act
      try {
        target.upsert(employee, personal, history);
      } catch (RuntimeException e) {
        // テストのために素通りさせる
      }
      // assert
      var actual = employeeMapper.selectAll();

//      softy.assertThatCode(
//          () -> Mockito.verify(this.employeeMapper, Mockito.times(1)).upsert(any())
//      ).as("EmployeeMapper#upsert() is called").doesNotThrowAnyException();
//      softy.assertThatCode(
//          () -> Mockito.verify(this.personalMapper, Mockito.times(1)).upsert(any())
//      ).as("PersonalMapper#upsert() is called").doesNotThrowAnyException();
//      softy.assertThatCode(
//          () -> Mockito.verify(this.historyMapper, Mockito.times(0)).bulkUpsert(any())
//      ).as("HistoryMapper#upsert() is called").doesNotThrowAnyException();
////      softy.assertThat(actual).as("upsert_personalMapper_Error: database compare")
////          .isEqualTo(expected);
    }
  }

}
