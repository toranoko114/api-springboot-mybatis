package com.course.api.springboot.mybatis.infrastructure.mapper.sub_company;

import com.course.api.springboot.mybatis.config.SubCompanyDataSourceConfig;
import com.course.api.springboot.mybatis.domain.entity.HistoryEntity;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@MybatisTest
@DBRider
@ActiveProfiles("test")
@ContextConfiguration(classes = SubCompanyDataSourceConfig.class)
class SubHistoryMapperTest {

  @Autowired
  private SubHistoryMapper target;

  @Nested
  @DisplayName("登録・更新テストクラス")
  @TestInstance(Lifecycle.PER_CLASS)
  class upsert {

    @Test
    @DisplayName("社員履歴情報登録")
    @DataSet(value = "datasets/SubHistoryMapperTest/input_data.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/SubHistoryMapperTest/bulkInsert_output_data.yml",
        orderBy = {"employee_id", "department_id"})
    void bulkInsert() {
      // arrange
      var entity = List.of(
          HistoryEntity.builder()
              .employeeId("sub-test")
              .startDate(LocalDate.of(2023, 2, 1))
              .departmentId(3)
              .content("テストBulkInsert")
              .build()
          , HistoryEntity.builder()
              .employeeId("test2")
              .startDate(LocalDate.of(2023, 2, 1))
              .departmentId(3)
              .content("テストBulkInsert")
              .build());
      // act
      target.bulkUpsert(entity);
    }

    @Test
    @DisplayName("社員履歴情報更新")
    @DataSet(value = "datasets/SubHistoryMapperTest/input_data.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/SubHistoryMapperTest/bulkUpdate_output_data.yml",
        orderBy = {"employee_id", "department_id"})
    void bulkUpsert() {
      // arrange
      var entity = List.of(
          HistoryEntity.builder()
              .employeeId("sub-test")
              .startDate(LocalDate.of(2023, 1, 1))
              .departmentId(1)
              .content("テストBulkUpdate")
              .build()
          , HistoryEntity.builder()
              .employeeId("test2")
              .startDate(LocalDate.of(2022, 2, 2))
              .departmentId(4)
              .content("テストBulkUpdate")
              .build());
      // act
      target.bulkUpsert(entity);
    }

  }

  @Nested
  @DisplayName("削除テストクラス")
  @TestInstance(Lifecycle.PER_CLASS)
  class delete {

    @Test
    @DisplayName("社員履歴情報削除-該当レコードあり")
    @DataSet(value = "datasets/SubHistoryMapperTest/input_data.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/SubHistoryMapperTest/delete_exist_record_output_data.yml",
        orderBy = {"employee_id", "department_id"})
    void delete_exist_record() {
      // act
      target.delete("test2");
    }

    @Test
    @DisplayName("社員履歴情報削除-該当レコードなし")
    @DataSet(value = "datasets/SubHistoryMapperTest/input_data.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/SubHistoryMapperTest/delete_no_record_output_data.yml",
        orderBy = {"employee_id", "department_id"})
    void delete_no_record() {
      // act
      target.delete("test3");
    }
  }
}
