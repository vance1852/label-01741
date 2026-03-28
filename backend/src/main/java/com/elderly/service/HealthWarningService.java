package com.elderly.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elderly.entity.HealthWarning;
import com.elderly.mapper.HealthWarningMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthWarningService {
    private final HealthWarningMapper healthWarningMapper;

    public List<HealthWarning> getActiveWarnings() {
        return healthWarningMapper.selectByStatusWithElder(0);
    }

    public IPage<HealthWarning> page(int current, int size) {
        return healthWarningMapper.selectPageWithElder(new Page<>(current, size));
    }

    public void saveBatch(List<HealthWarning> warnings) {
        for (HealthWarning warning : warnings) {
            healthWarningMapper.insert(warning);
        }
    }

    public void handleWarning(Long id) {
        HealthWarning warning = healthWarningMapper.selectById(id);
        if (warning != null) {
            warning.setStatus(1);
            warning.setHandledTime(LocalDateTime.now());
            healthWarningMapper.updateById(warning);
        }
    }
}
