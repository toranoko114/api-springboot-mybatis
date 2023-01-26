package com.course.apispringbootmybatis.infrastructure.mapper;


import com.course.apispringbootmybatis.domain.dto.EmployeeDto;
import com.course.apispringbootmybatis.domain.dto.HistoryDto;
import com.course.apispringbootmybatis.domain.dto.PersonalDto;
import com.course.apispringbootmybatis.domain.entity.EmployeeEntity;
import com.course.apispringbootmybatis.enums.Department;
import com.course.apispringbootmybatis.enums.Gender;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@DBRider
@ActiveProfiles("test")
@MybatisTest
class EmployeeMapperTest {

  @Autowired
  private EmployeeMapper target;

  @Nested
  @DisplayName("検索系テストクラス")
  @TestInstance(Lifecycle.PER_CLASS)
  class select {

    // クラス内で共通で利用する変数を定義
    List<HistoryDto> history_1 = List.of(
        HistoryDto.builder()
            .employeeId("test")
            .departmentId(2)
            .startDate(LocalDate.of(2022, 1, 1))
            .content("テスト入社")
            .build(),
        HistoryDto.builder()
            .employeeId("test")
            .departmentId(1)
            .startDate(LocalDate.of(2023, 1, 1))
            .content("テスト退職").build()
    );

    List<HistoryDto> history_2 = List.of(
        HistoryDto.builder()
            .employeeId("test2")
            .departmentId(4)
            .startDate(LocalDate.of(2022, 2, 2))
            .content("テスト2入社").build()
    );

    PersonalDto personal_1 = PersonalDto.builder()
        .employeeId("test")
        .birthday(LocalDate.of(2020, 1, 1))
        .telephoneNumber("00000000001")
        .mailAddress("test@gmail.com").build();

    PersonalDto personal_2 = PersonalDto.builder()
        .employeeId("test2")
        .birthday(LocalDate.of(2020, 2, 2))
        .telephoneNumber("00000000002")
        .mailAddress("test2@gmail.com").build();

    @Test
    @DisplayName("社員情報検索（ID指定）")
    @DataSet("datasets/EmployeeMapperTest/select_input_data.yml")
    void selectById() {
      // arrange
      var expected = Optional.of(
          EmployeeDto.builder()
              .employeeId("test")
              .employeeName("テスト")
              .gender(Gender.FEMALE)
              .department(Department.INDEPENDENT)
              .personal(personal_1)
              .historyList(history_1)
              .build()
      );
      // act
      var actual = target.selectById("test");
      // assert
      Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("社員情報全件検索")
    @DataSet("datasets/EmployeeMapperTest/select_input_data.yml")
    void selectAll() {
      // arrange
      var expected = List.of(
          EmployeeDto.builder()
              .employeeId("test")
              .employeeName("テスト")
              .gender(Gender.FEMALE)
              .department(Department.INDEPENDENT)
              .personal(personal_1)
              .historyList(history_1)
              .build(),
          EmployeeDto.builder()
              .employeeId("test2")
              .employeeName("テスト2")
              .gender(Gender.MALE)
              .department(Department.DEVELOPMENT)
              .personal(personal_2)
              .historyList(history_2)
              .build()
      );
      // act
      var actual = target.selectAll();
      // assert
      Assertions.assertEquals(expected, actual);
    }
  }

  @Nested
  @DisplayName("登録・更新テストクラス")
  @TestInstance(Lifecycle.PER_CLASS)
  class upsert {

    @Test
    @DataSet("datasets/EmployeeMapperTest/upsert_input_data.yml")
    @ExpectedDataSet(value = "datasets/EmployeeMapperTest/upsert_output_data.yml",
        orderBy = {"employee_id", "department_id"})
    void insert() {
      // arrange
      var entity = EmployeeEntity.builder()
          .employeeId("insert_emp")
          .employeeName("登録テスト")
          .departmentId(3)
          .gender("MALE")
          .build();
      // act
      target.upsert(entity);
    }

  }


}
