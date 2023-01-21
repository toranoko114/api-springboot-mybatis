package com.course.apispringbootmybatis.domain.service;

import com.course.apispringbootmybatis.domain.dto.HistoryDto;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import java.util.List;

public interface HistoryService {

  List<HistoryDto> selectById(int employeeId);

  int create(HistoryEntity historyEntity);

  int upsert(HistoryEntity historyEntity);


}
