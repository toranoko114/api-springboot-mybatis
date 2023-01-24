package com.course.apispringbootmybatis.infrastructure.mapper;


import com.course.apispringbootmybatis.domain.dto.EmployeeDto;
import com.course.apispringbootmybatis.domain.dto.HistoryDto;
import com.course.apispringbootmybatis.domain.dto.PersonalDataDto;
import com.course.apispringbootmybatis.enums.Department;
import com.course.apispringbootmybatis.enums.Gender;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@DBRider
@ActiveProfiles("test")
@MybatisTest
class EmployeeMapperTest {

  @Autowired
  private EmployeeMapper target;

  @Test
  @DisplayName("社員情報検索（ID指定）")
  @DataSet("datasets/data.yml")
  void selectById() {

    // arrange
    var history = List.of(
        HistoryDto.builder()
            .historyId(1)
            .employeeId(1)
            .departmentId(2)
            .startDate(LocalDate.of(2022, 1, 1))
            .content("テスト入社")
            .build(),
        HistoryDto.builder()
            .historyId(2)
            .employeeId(1)
            .departmentId(1)
            .startDate(LocalDate.of(2023, 1, 1))
            .content("テスト退職").build()
    );

    var personal = PersonalDataDto.builder()
        .employeeId(1)
        .birthday(LocalDate.of(2020, 1, 1))
        .telephoneNumber("00000000000")
        .mailAddress("test@gmail.com").build();

    var expected = Optional.of(
        EmployeeDto.builder()
            .employeeId(1)
            .employeeName("テスト")
            .gender(Gender.FEMALE)
            .department(Department.INDEPENDENT)
            .personal(personal)
            .historyList(history)
            .build()
    );

    // act
    var actual = target.selectById(1);

    // assert
    Assertions.assertEquals(expected, actual);
  }

}
