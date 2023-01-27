package com.course.apispringbootmybatis.infrastructure.mapper;

import com.course.apispringbootmybatis.domain.entity.PersonalEntity;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@MybatisTest
@DBRider
@ActiveProfiles("test")
class PersonalMapperTest {

  @Autowired
  private PersonalMapper target;

  @Nested
  @DisplayName("登録・更新テストクラス")
  @TestInstance(Lifecycle.PER_CLASS)
  class upsert {

    @Test
    @DisplayName("社員個人情報登録")
    @DataSet("datasets/PersonalMapperTest/input_data.yml")
    @ExpectedDataSet(value = "datasets/PersonalMapperTest/insert_output_data.yml",
        orderBy = {"employee_id", "department_id"})
    void insert() {
      // arrange
      var entity = PersonalEntity.builder()
          .employeeId("test3")
          .birthday(LocalDate.of(2020, 3, 3))
          .telephoneNumber("00000000003")
          .mailAddress("test3@gmail.com")
          .build();
      // act
      target.upsert(entity);
    }

    @Test
    @DisplayName("社員個人情報更新")
    @DataSet("datasets/PersonalMapperTest/input_data.yml")
    @ExpectedDataSet(value = "datasets/PersonalMapperTest/update_output_data.yml",
        orderBy = {"employee_id", "department_id"})
    void update() {
      // arrange
      var entity = PersonalEntity.builder()
          .employeeId("test2")
          // 誕生日は変更されない想定
          .birthday(LocalDate.of(2020, 3, 3))
          .telephoneNumber("10000000002")
          .mailAddress("test2-updata@gmail.com")
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
    @DisplayName("社員個人情報削除-該当レコードあり")
    @DataSet("datasets/PersonalMapperTest/input_data.yml")
    @ExpectedDataSet(value = "datasets/PersonalMapperTest/delete_exist_record_output_data.yml",
        orderBy = {"employee_id", "department_id"})
    void delete_exist_record() {
      // act
      target.delete("test2");
    }

    @Test
    @DisplayName("社員個人情報削除-該当レコードなし")
    @DataSet("datasets/PersonalMapperTest/input_data.yml")
    @ExpectedDataSet(value = "datasets/PersonalMapperTest/delete_no_record_output_data.yml",
        orderBy = {"employee_id", "department_id"})
    void delete_no_record() {
      // act
      target.delete("test3");
    }

  }


}
