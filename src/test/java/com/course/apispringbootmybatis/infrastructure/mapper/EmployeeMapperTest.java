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
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@DBRider
@ActiveProfiles("test")
@MybatisTest
@ExtendWith(SoftAssertionsExtension.class)
class EmployeeMapperTest {

  @InjectSoftAssertions
  private SoftAssertions softy;

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
    @DisplayName("社員情報ID検索-該当レコードあり")
    @DataSet(value = "datasets/EmployeeMapperTest/input_data.yml", cleanBefore = true)
    void selectById_exist_record() {
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
      softy.assertThat(actual).as("selectById_exist_record: database compare").isEqualTo(expected);
    }

    @Test
    @DisplayName("社員情報ID検索-該当レコードなし")
    @DataSet(value = "datasets/EmployeeMapperTest/input_data.yml", cleanBefore = true)
    void selectById_no_record() {
      // arrange
      var expected = Optional.empty();
      // act
      var actual = target.selectById("notExist");
      // assert
      softy.assertThat(actual).as("selectById_no_record: database compare").isEqualTo(expected);
    }

    @Test
    @DisplayName("社員情報全件検索-レコードあり")
    @DataSet(value = "datasets/EmployeeMapperTest/input_data.yml", cleanBefore = true)
    void selectAll_exist_record() {
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
      softy.assertThat(actual).as("selectAll_exist_record: database compare").isEqualTo(expected);
    }

    @Test
    @DisplayName("社員情報全件検索-レコードなし")
    @DataSet(value = "datasets/EmployeeMapperTest/input_no_data.yml", cleanBefore = true)
    void selectAll_no_record() {
      // arrange
      var expected = List.of();
      // act
      var actual = target.selectAll();
      // assert
      softy.assertThat(actual).as("selectAll_no_record: database compare").isEqualTo(expected);
    }
  }

  @Nested
  @DisplayName("登録・更新テストクラス")
  @TestInstance(Lifecycle.PER_CLASS)
  class upsert {

    @Test
    @DisplayName("社員情報登録")
    @DataSet(value = "datasets/EmployeeMapperTest/input_data.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/EmployeeMapperTest/insert_output_data.yml",
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

    @Test
    @DisplayName("社員情報更新")
    @DataSet(value = "datasets/EmployeeMapperTest/input_data.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/EmployeeMapperTest/update_output_data.yml",
        orderBy = {"employee_id", "department_id"})
    void update() {
      // arrange
      var entity = EmployeeEntity.builder()
          .employeeId("test2")
          .employeeName("更新テスト")
          .departmentId(1)
          .gender("FEMALE")
          .build();
      // act
      target.upsert(entity);
    }

  }

  @Nested
  @DisplayName("削除テストクラス")
  @TestInstance(Lifecycle.PER_CLASS)
  class delete {

    @Test
    @DisplayName("社員情報削除-該当レコードあり")
    @DataSet(value = "datasets/EmployeeMapperTest/input_data.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/EmployeeMapperTest/delete_exist_record_output_data.yml",
        orderBy = {"employee_id", "department_id"})
    void delete_exist_record() {
      // act
      target.delete("test2");
    }

    @Test
    @DisplayName("社員情報削除-該当レコードなし")
    @DataSet(value = "datasets/EmployeeMapperTest/input_data.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/EmployeeMapperTest/delete_no_record_output_data.yml",
        orderBy = {"employee_id", "department_id"})
    void delete_no_record() {
      // act
      target.delete("test3");
    }

  }

}
