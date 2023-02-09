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
import java.util.stream.Stream;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

@DBRider
@MybatisTest
@ActiveProfiles("test")
@ExtendWith(SoftAssertionsExtension.class)
class EmployeeLogicImplTest {

  @InjectSoftAssertions
  private SoftAssertions softy;
  @SpyBean // ( @Spy + @Autowired )
  private EmployeeLogicImpl target;
  @SpyBean
  private EmployeeMapper employeeMapper;
  @SpyBean
  private HistoryMapper historyMapper;
  @SpyBean
  private PersonalMapper personalMapper;

  private static final String PROVIDER_PATH =
      "com.course.apispringbootmybatis.domain.service.logic.impl.EmployeeLogicImplTest#provider_entity";

  private static Stream<Arguments> provider_entity() {
    return Stream.of(
        Arguments.of(
            EmployeeEntity.builder().employeeId("test2").employeeName("テスト2")
                .departmentId(4).gender("MALE").build(),
            PersonalEntity.builder().employeeId("test2")
                .birthday(LocalDate.of(2020, 2, 2))
                .telephoneNumber("00000000002").mailAddress("test2@gmail.com").build(),
            List.of(
                HistoryEntity.builder().employeeId("test2").startDate(LocalDate.of(2022, 2, 2))
                    .departmentId(4).content("テスト2入社").build())
        )
    );
  }

  /**
   * テストメソッド実行前にMockを初期化する 実行順序変更によるJUnitテスト失敗を防ぐため
   */
  @BeforeEach
  public void doBeforeEach() {
    Mockito.reset(employeeMapper);
    Mockito.reset(historyMapper);
    Mockito.reset(personalMapper);
  }

  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  @DisplayName("トランザクション-正常系")
  class transactionNormal {

    @ParameterizedTest
    @MethodSource("provider_insert")
    @DisplayName("トランザクション-正常系-登録")
    @DataSet(value = "datasets/EmployeeLogicImplTest/input_data.yml", cleanBefore = true)
    void insert(EmployeeEntity employee, PersonalEntity personal, List<HistoryEntity> history,
        List<EmployeeDto> expected) {
      // act
      target.upsert(employee, personal, history);
      // assert
      var actual = employeeMapper.selectAll();
      softy.assertThat(actual).as("transactionNormal_insert: database compare").isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("provider_update")
    @DisplayName("トランザクション-正常系-更新")
    @DataSet(value = "datasets/EmployeeLogicImplTest/input_data.yml", cleanBefore = true)
    void update(EmployeeEntity employee, PersonalEntity personal, List<HistoryEntity> history,
        List<EmployeeDto> expected) {
      // act
      target.upsert(employee, personal, history);
      // assert
      var actual = employeeMapper.selectAll();
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

    private Stream<Arguments> provider_insert() {
      return Stream.of(
          Arguments.of(
              EmployeeEntity.builder().employeeId("test2").employeeName("テスト2")
                  .departmentId(4).gender("MALE").build(),
              PersonalEntity.builder().employeeId("test2")
                  .birthday(LocalDate.of(2020, 2, 2))
                  .telephoneNumber("00000000002").mailAddress("test2@gmail.com").build(),
              List.of(
                  HistoryEntity.builder().employeeId("test2").startDate(LocalDate.of(2022, 2, 2))
                      .departmentId(4).content("テスト2入社").build()),
              List.of(
                  EmployeeDto.builder().employeeId("test")
                      .department(Department.INDEPENDENT).employeeName("テスト").gender(Gender.FEMALE)
                      .personal(
                          PersonalDto.builder().employeeId("test")
                              .birthday(LocalDate.of(2020, 1, 1))
                              .telephoneNumber("00000000001").mailAddress("test@gmail.com").build())
                      .historyList(List.of(
                          HistoryDto.builder().employeeId("test")
                              .startDate(LocalDate.of(2022, 1, 1))
                              .departmentId(2).content("テスト入社").build(),
                          HistoryDto.builder().employeeId("test")
                              .startDate(LocalDate.of(2023, 1, 1))
                              .departmentId(1).content("テスト退職").build())).build(),
                  EmployeeDto.builder().employeeId("test2")
                      .department(Department.DEVELOPMENT).employeeName("テスト2").gender(Gender.MALE)
                      .personal(
                          PersonalDto.builder().employeeId("test2")
                              .birthday(LocalDate.of(2020, 2, 2))
                              .telephoneNumber("00000000002").mailAddress("test2@gmail.com")
                              .build())
                      .historyList(List.of(
                          HistoryDto.builder().employeeId("test2")
                              .startDate(LocalDate.of(2022, 2, 2))
                              .departmentId(4).content("テスト2入社").build())).build()
              )
          )
      );
    }

    private Stream<Arguments> provider_update() {
      return Stream.of(
          Arguments.of(
              EmployeeEntity.builder().employeeId("test").employeeName("テスト更新")
                  .departmentId(3).gender("MALE").build(),
              PersonalEntity.builder().employeeId("test").birthday(LocalDate.of(2020, 1, 1))
                  .telephoneNumber("00000000002").mailAddress("test-update@gmail.com").build(),
              List.of(
                  HistoryEntity.builder().employeeId("test").startDate(LocalDate.of(2022, 1, 1))
                      .departmentId(2).content("テスト入社").build(),
                  HistoryEntity.builder().employeeId("test").startDate(LocalDate.of(2023, 1, 1))
                      .departmentId(1).content("テスト退職-更新").build(),
                  HistoryEntity.builder().employeeId("test").startDate(LocalDate.of(2024, 1, 1))
                      .departmentId(3).content("テスト退職-追加").build()),
              List.of(
                  EmployeeDto.builder().employeeId("test")
                      .department(Department.PLANNING).employeeName("テスト更新").gender(Gender.MALE)
                      .personal(
                          PersonalDto.builder().employeeId("test")
                              .birthday(LocalDate.of(2020, 1, 1))
                              .telephoneNumber("00000000002").mailAddress("test-update@gmail.com")
                              .build())
                      .historyList(List.of(
                          HistoryDto.builder().employeeId("test")
                              .startDate(LocalDate.of(2022, 1, 1))
                              .departmentId(2).content("テスト入社").build(),
                          HistoryDto.builder().employeeId("test")
                              .startDate(LocalDate.of(2023, 1, 1))
                              .departmentId(1).content("テスト退職-更新").build(),
                          HistoryDto.builder().employeeId("test")
                              .startDate(LocalDate.of(2024, 1, 1))
                              .departmentId(3).content("テスト退職-追加").build())).build()
              )
          )
      );
    }
  }

  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  @DisplayName("トランザクション-異常系")
  class transactionAbnormal {

    @ParameterizedTest
    @MethodSource(PROVIDER_PATH)
    @DisplayName("トランザクション-異常系-Employee登録時にエラー")
    @DataSet(value = "datasets/EmployeeLogicImplTest/input_data.yml", cleanBefore = true)
    void upsert_employeeMapper_Error(EmployeeEntity employee, PersonalEntity personal,
        List<HistoryEntity> history) {

      // arrange
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
          () -> Mockito.verify(employeeMapper, Mockito.times(1)).upsert(any())
      ).as("EmployeeMapper#upsert() is called").doesNotThrowAnyException();
      softy.assertThatCode(
          () -> Mockito.verify(personalMapper, Mockito.times(0)).upsert(any())
      ).as("PersonalMapper#upsert() is called").doesNotThrowAnyException();
      softy.assertThatCode(
          () -> Mockito.verify(historyMapper, Mockito.times(0)).bulkUpsert(any())
      ).as("HistoryMapper#upsert() is called").doesNotThrowAnyException();
      softy.assertThat(actual).as("upsert_employeeMapper_Error: database compare")
          .isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource(PROVIDER_PATH)
    @DisplayName("トランザクション-異常系-Personal登録時にエラー")
    @DataSet(value = "datasets/EmployeeLogicImplTest/input_data.yml", cleanBefore = true)
    void upsert_personalMapper_Error(EmployeeEntity employee, PersonalEntity personal,
        List<HistoryEntity> history) {

      // arrange
      Mockito.doThrow(new RuntimeException()).when(personalMapper).upsert(personal);
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
          () -> Mockito.verify(employeeMapper, Mockito.times(1)).upsert(any())
      ).as("EmployeeMapper#upsert() is called").doesNotThrowAnyException();
      softy.assertThatCode(
          () -> Mockito.verify(personalMapper, Mockito.times(1)).upsert(any())
      ).as("PersonalMapper#upsert() is called").doesNotThrowAnyException();
      softy.assertThatCode(
          () -> Mockito.verify(historyMapper, Mockito.times(0)).bulkUpsert(any())
      ).as("HistoryMapper#upsert() is called").doesNotThrowAnyException();
      softy.assertThat(actual).as("upsert_personalMapper_Error: database compare")
          .isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource(PROVIDER_PATH)
    @DisplayName("トランザクション-異常系-History登録時にエラー")
    @DataSet(value = "datasets/EmployeeLogicImplTest/input_data.yml", cleanBefore = true)
    void upsert_historyMapper_Error(EmployeeEntity employee, PersonalEntity personal,
        List<HistoryEntity> history) {

      // arrange
      Mockito.doThrow(new RuntimeException()).when(historyMapper).bulkUpsert(history);
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
          () -> Mockito.verify(employeeMapper, Mockito.times(1)).upsert(any())
      ).as("EmployeeMapper#upsert() is called").doesNotThrowAnyException();
      softy.assertThatCode(
          () -> Mockito.verify(personalMapper, Mockito.times(1)).upsert(any())
      ).as("PersonalMapper#upsert() is called").doesNotThrowAnyException();
      softy.assertThatCode(
          () -> Mockito.verify(historyMapper, Mockito.times(1)).bulkUpsert(any())
      ).as("HistoryMapper#upsert() is called").doesNotThrowAnyException();
      softy.assertThat(actual).as("upsert_personalMapper_Error: database compare")
          .isEqualTo(expected);
    }

    @Test
    @DisplayName("トランザクション-異常系-Employee削除時にエラー")
    @DataSet(value = "datasets/EmployeeLogicImplTest/input_data.yml", cleanBefore = true)
    void delete_employeeMapper_Error() {

      // arrange
      Mockito.doThrow(new RuntimeException()).when(employeeMapper).delete("test");
      var expected = employeeMapper.selectAll();

      // act
      try {
        target.deleteById("test");
      } catch (RuntimeException e) {
        // テストのために素通りさせる
      }
      // assert
      var actual = employeeMapper.selectAll();

      softy.assertThatCode(
          () -> Mockito.verify(employeeMapper, Mockito.times(1)).delete(any())
      ).as("EmployeeMapper#delete() is called").doesNotThrowAnyException();
      softy.assertThatCode(
          () -> Mockito.verify(personalMapper, Mockito.times(0)).delete(any())
      ).as("PersonalMapper#delete() is called").doesNotThrowAnyException();
      softy.assertThatCode(
          () -> Mockito.verify(historyMapper, Mockito.times(0)).delete(any())
      ).as("HistoryMapper#delete() is called").doesNotThrowAnyException();
      softy.assertThat(actual).as("delete_employeeMapper_Error: database compare")
          .isEqualTo(expected);
    }

    @Test
    @DisplayName("トランザクション-異常系-Personal削除時にエラー")
    @DataSet(value = "datasets/EmployeeLogicImplTest/input_data.yml", cleanBefore = true)
    void delete_personalMapper_Error() {

      // arrange
      Mockito.doThrow(new RuntimeException()).when(personalMapper).delete("test");
      var expected = employeeMapper.selectAll();

      // act
      try {
        target.deleteById("test");
      } catch (RuntimeException e) {
        // テストのために素通りさせる
      }
      // assert
      var actual = employeeMapper.selectAll();

      softy.assertThatCode(
          () -> Mockito.verify(employeeMapper, Mockito.times(1)).delete(any())
      ).as("EmployeeMapper#delete() is called").doesNotThrowAnyException();
      softy.assertThatCode(
          () -> Mockito.verify(personalMapper, Mockito.times(1)).delete(any())
      ).as("PersonalMapper#delete() is called").doesNotThrowAnyException();
      softy.assertThatCode(
          () -> Mockito.verify(historyMapper, Mockito.times(0)).delete(any())
      ).as("HistoryMapper#delete() is called").doesNotThrowAnyException();
      softy.assertThat(actual).as("delete_personalMapper_Error: database compare")
          .isEqualTo(expected);
    }

    @Test
    @DisplayName("トランザクション-異常系-History削除時にエラー")
    @DataSet(value = "datasets/EmployeeLogicImplTest/input_data.yml", cleanBefore = true)
    void delete_historyMapper_Error() {

      // arrange
      Mockito.doThrow(new RuntimeException()).when(historyMapper).delete("test");
      var expected = employeeMapper.selectAll();

      // act
      try {
        target.deleteById("test");
      } catch (RuntimeException e) {
        // テストのために素通りさせる
      }
      // assert
      var actual = employeeMapper.selectAll();

      softy.assertThatCode(
          () -> Mockito.verify(employeeMapper, Mockito.times(1)).delete(any())
      ).as("EmployeeMapper#delete() is called").doesNotThrowAnyException();
      softy.assertThatCode(
          () -> Mockito.verify(personalMapper, Mockito.times(1)).delete(any())
      ).as("PersonalMapper#delete() is called").doesNotThrowAnyException();
      softy.assertThatCode(
          () -> Mockito.verify(historyMapper, Mockito.times(1)).delete(any())
      ).as("HistoryMapper#delete() is called").doesNotThrowAnyException();
      softy.assertThat(actual).as("delete_historyMapper_Error: database compare")
          .isEqualTo(expected);
    }
  }
}
