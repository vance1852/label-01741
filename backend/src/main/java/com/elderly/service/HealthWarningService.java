package com.elderly.service;

import com.elderly.config.HealthWarningConfig;
import com.elderly.entity.HealthRecord;
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
    private final HealthWarningConfig warningConfig;

    public void checkAndCreateWarnings(HealthRecord record) {
        List<HealthWarningConfig.WarningRule> rules = warningConfig.getWarningRules();
        
        for (HealthWarningConfig.WarningRule rule : rules) {
            if (rule.getCondition().isTriggered(record)) {
                HealthWarning warning = new HealthWarning();
                warning.setElderId(record.getElderId());
                warning.setHealthRecordId(record.getId());
                warning.setIndicator(rule.getIndicator());
                warning.setWarningLevel(rule.getLevel());
                warning.setMessage(buildWarningMessage(rule.getIndicator(), record));
                warning.setCreateTime(LocalDateTime.now());
                
                healthWarningMapper.insert(warning);
            }
        }
    }

    private String buildWarningMessage(String indicator, HealthRecord record) {
        StringBuilder sb = new StringBuilder();
        sb.append("老人").append("的").append(indicator).append("指标异常: ");
        
        switch (indicator) {
            case "高血压":
                sb.append("收缩压 ").append(record.getBloodPressureHigh())
                  .append(", 舒张压 ").append(record.getBloodPressureLow());
                break;
            case "高血糖":
                sb.append("血糖值 ").append(record.getBloodSugar());
                break;
            case "发热":
                sb.append("体温 ").append(record.getTemperature());
                break;
            case "心率异常":
                sb.append("心率 ").append(record.getHeartRate());
                break;
        }
        
        return sb.toString();
    }

    public List<HealthWarning> getRecentWarnings() {
        return healthWarningMapper.selectRecentWarningsWithElderName();
    }
}
