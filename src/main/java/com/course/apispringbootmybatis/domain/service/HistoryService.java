package com.course.apispringbootmybatis.domain.service;

import com.course.apispringbootmybatis.domain.dto.HistoryDto;
import com.course.apispringbootmybatis.domain.entity.HistoryEntity;
import java.util.List;
import java.util.Optional;

public interface HistoryService {

  Optional<List<HistoryDto>> selectById(int userId);

  int create(HistoryEntity historyEntity);

  int upsert(HistoryEntity historyEntity);


}
