package com.elderly.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elderly.entity.HealthRecord;
import com.elderly.entity.HealthWarning;
import com.elderly.mapper.HealthRecordMapper;
import com.elderly.strategy.HealthWarningStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthRecordService {
    private final HealthRecordMapper healthRecordMapper;
    private final HealthWarningStrategy healthWarningStrategy;
    private final HealthWarningService healthWarningService;

    public IPage<HealthRecord> page(int current, int size) {
        return healthRecordMapper.selectPageWithElder(new Page<>(current, size));
    }

    public List<HealthRecord> listByElderId(Long elderId) {
        return healthRecordMapper.selectList(new LambdaQueryWrapper<HealthRecord>()
                .eq(HealthRecord::getElderId, elderId)
                .orderByDesc(HealthRecord::getRecordTime));
    }

    @Transactional
    public void save(HealthRecord record) {
        if (record.getRecordTime() == null) {
            record.setRecordTime(LocalDateTime.now());
        }
        healthRecordMapper.insert(record);
        
        List<HealthWarning> warnings = healthWarningStrategy.check(record);
        if (!warnings.isEmpty()) {
            healthWarningService.saveBatch(warnings);
        }
    }
}
