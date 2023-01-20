package com.course.apispringbootmybatis.infrastructure.mapper;

import com.course.apispringbootmybatis.domain.dto.PersonalDataDto;
import com.course.apispringbootmybatis.domain.entity.PersonalDataEntity;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PersonalDataMapper {

  @Insert("INSERT INTO PERSONAL_DATA "
      + "(EMPLOYEE_ID, BIRTHDAY, TELEPHONE_NUMBER, MAIL_ADDRESS) "
      + "VALUES (#{employeeId}, #{birthday}, #{telephoneNumber}, #{mailAddress})")
  void insert(PersonalDataEntity personal);

  @Select("SELECT EMPLOYEE_ID, DEPARTMENT_ID, CONTENT "
      + "FROM HISTORY "
      + "WHERE EMPLOYEE_ID = #{employeeId}")
  List<PersonalDataDto> selectById(Integer employeeId);

}
