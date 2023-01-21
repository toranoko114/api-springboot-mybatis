package com.course.apispringbootmybatis.domain.service.impl;

import com.course.apispringbootmybatis.domain.dto.HistoryDto;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import com.course.apispringbootmybatis.domain.service.HistoryService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl implements HistoryService {

  @Override
  public List<HistoryDto> selectById(int employeeId) {
    return List.of();
  }

  @Override
  public int create(HistoryEntity historyEntity) {
    return 0;
  }

  @Override
  public int upsert(HistoryEntity historyEntity) {
    return 0;
  }
}
