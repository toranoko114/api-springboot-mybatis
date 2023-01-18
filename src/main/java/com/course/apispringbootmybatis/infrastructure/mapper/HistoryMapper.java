package com.course.apispringbootmybatis.infrastructure.mapper;

import com.course.apispringbootmybatis.domain.dto.HistoryDto;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HistoryMapper {

  @Insert("INSERT INTO HISTORY (EMPLOYEE_ID, DEPARTMENT_ID, CONTENT) VALUES (#{employeeId}, #{departmentId}, #{content})")
  void insert(HistoryEntity history);

  @Select("SELECT EMPLOYEE_ID, DEPARTMENT_ID, CONTENT FROM HISTORY WHERE EMPLOYEE_ID = #{employeeId}")
  List<HistoryDto> selectById(Integer employeeId);

}
