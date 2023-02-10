package com.course.apispringbootmybatis.infrastructure.mapper.sub_company;


import com.course.apispringbootmybatis.config.SubCompanyDataSourceConfig;
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
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@DBRider
@ActiveProfiles("test")
@MybatisTest
@ExtendWith(SoftAssertionsExtension.class)
@ContextConfiguration(classes = SubCompanyDataSourceConfig.class)
class SubEmployeeMapperTest {

  @InjectSoftAssertions
  private SoftAssertions softy;

  @Autowired
  private SubEmployeeMapper target;

  @Nested
  @DisplayName("検索系テストクラス")
  @TestInstance(Lifecycle.PER_CLASS)
  class select {

    @ParameterizedTest(name = "{0}")
    @MethodSource("provider_expected_data")
    @DisplayName("社員情報ID検索-該当レコードあり")
    @DataSet(value = "datasets/SubEmployeeMapperTest/input_data.yml", cleanBefore = true)
    void selectById_exist_record(EmployeeDto emp) {
      // arrange
      var expected = Optional.of(emp);
      // act
      var actual = target.selectById("sub-test");
      // assert
      softy.assertThat(actual).as("selectById_exist_record: database compare").isEqualTo(expected);
    }

    @Test
    @DisplayName("社員情報ID検索-該当レコードなし")
    @DataSet(value = "datasets/SubEmployeeMapperTest/input_data.yml", cleanBefore = true)
    void selectById_no_record() {
      // arrange
      var expected = Optional.empty();
      // act
      var actual = target.selectById("notExist");
      // assert
      softy.assertThat(actual).as("selectById_no_record: database compare").isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provider_expected_data")
    @DisplayName("社員情報全件検索-レコードあり")
    @DataSet(value = "datasets/SubEmployeeMapperTest/input_data.yml", cleanBefore = true)
    void selectAll_exist_record(EmployeeDto emp, EmployeeDto emp2) {
      // arrange
      var expected = List.of(emp, emp2);
      // act
      var actual = target.selectAll();
      // assert
      softy.assertThat(actual).as("selectAll_exist_record: database compare").isEqualTo(expected);
    }

    @Test
    @DisplayName("社員情報全件検索-レコードなし")
    @DataSet(value = "datasets/SubEmployeeMapperTest/input_no_data.yml", cleanBefore = true)
    void selectAll_no_record() {
      // arrange
      var expected = List.of();
      // act
      var actual = target.selectAll();
      // assert
      softy.assertThat(actual).as("selectAll_no_record: database compare").isEqualTo(expected);
    }

    // Classに@TestInstance(Lifecycle.PER_CLASS)をつければ非staticにできる
    public Stream<Arguments> provider_expected_data() {
      var personal1 = PersonalDto.builder().employeeId("sub-test").birthday(LocalDate.of(2020, 1, 1))
          .telephoneNumber("00000000001").mailAddress("test@gmail.com").build();
      var personal2 = PersonalDto.builder().employeeId("test2").birthday(LocalDate.of(2020, 2, 2))
          .telephoneNumber("00000000002").mailAddress("test2@gmail.com").build();
      var history1 = List.of(HistoryDto.builder().employeeId("sub-test").departmentId(2)
              .startDate(LocalDate.of(2022, 1, 1)).content("テスト入社").build(),
          HistoryDto.builder().employeeId("sub-test").departmentId(1)
              .startDate(LocalDate.of(2023, 1, 1)).content("テスト退職").build());
      var history2 = List.of(HistoryDto.builder().employeeId("test2").departmentId(4)
          .startDate(LocalDate.of(2022, 2, 2)).content("テスト2入社").build());
      return Stream.of(Arguments.of(
          EmployeeDto.builder().employeeId("sub-test").employeeName("テスト").gender(Gender.FEMALE)
              .department(Department.INDEPENDENT).personal(personal1).historyList(history1).build(),
          EmployeeDto.builder().employeeId("test2").employeeName("テスト2").gender(Gender.MALE)
              .department(Department.DEVELOPMENT).personal(personal2).historyList(history2)
              .build()));
    }
  }

  @Nested
  @DisplayName("登録・更新テストクラス")
  @TestInstance(Lifecycle.PER_CLASS)
  class upsert {

    @Test
    @DisplayName("社員情報登録")
    @DataSet(value = "datasets/SubEmployeeMapperTest/input_data.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/SubEmployeeMapperTest/insert_output_data.yml", orderBy = {
        "employee_id", "department_id"})
    void insert() {
      // arrange
      var entity = EmployeeEntity.builder().employeeId("insert_emp").employeeName("登録テスト")
          .departmentId(3).gender("MALE").build();
      // act
      target.upsert(entity);
    }

    @Test
    @DisplayName("社員情報更新")
    @DataSet(value = "datasets/SubEmployeeMapperTest/input_data.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/SubEmployeeMapperTest/update_output_data.yml", orderBy = {
        "employee_id", "department_id"})
    void update() {
      // arrange
      var entity = EmployeeEntity.builder().employeeId("test2").employeeName("更新テスト")
          .departmentId(1).gender("FEMALE").build();
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
    @DataSet(value = "datasets/SubEmployeeMapperTest/input_data.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/SubEmployeeMapperTest/delete_exist_record_output_data.yml", orderBy = {
        "employee_id", "department_id"})
    void delete_exist_record() {
      // act
      target.delete("test2");
    }

    @Test
    @DisplayName("社員情報削除-該当レコードなし")
    @DataSet(value = "datasets/SubEmployeeMapperTest/input_data.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/SubEmployeeMapperTest/delete_no_record_output_data.yml", orderBy = {
        "employee_id", "department_id"})
    void delete_no_record() {
      // act
      target.delete("test3");
    }
  }
}
