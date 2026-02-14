package com.elderly.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elderly.entity.HealthRecord;
import com.elderly.mapper.HealthRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthRecordService {
    private final HealthRecordMapper healthRecordMapper;

    public IPage<HealthRecord> page(int current, int size) {
        return healthRecordMapper.selectPageWithElder(new Page<>(current, size));
    }

    public List<HealthRecord> listByElderId(Long elderId) {
        return healthRecordMapper.selectList(new LambdaQueryWrapper<HealthRecord>()
                .eq(HealthRecord::getElderId, elderId)
                .orderByDesc(HealthRecord::getRecordTime));
    }

    public void save(HealthRecord record) {
        healthRecordMapper.insert(record);
    }
}
