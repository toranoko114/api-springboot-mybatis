package com.course.apispringbootmybatis.infrastructure.mapper;

import com.course.apispringbootmybatis.domain.dto.HistoryDto;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HistoryMapper {

  @Insert("INSERT INTO HISTORY "
      + "(START_DATE, EMPLOYEE_ID, DEPARTMENT_ID, CONTENT) "
      + "VALUES (#{startDate}, #{employeeId}, #{departmentId}, #{content})")
  void insert(HistoryEntity history);

  @Select("SELECT * WHERE EMPLOYEE_ID = #{employeeId}")
  List<HistoryDto> selectById(Integer employeeId);

}
