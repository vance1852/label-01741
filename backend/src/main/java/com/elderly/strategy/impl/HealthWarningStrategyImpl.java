package com.elderly.strategy.impl;

import com.elderly.config.HealthWarningProperties;
import com.elderly.entity.HealthRecord;
import com.elderly.entity.HealthWarning;
import com.elderly.strategy.HealthWarningStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HealthWarningStrategyImpl implements HealthWarningStrategy {
    
    private final HealthWarningProperties warningProperties;
    
    @Override
    public List<HealthWarning> check(HealthRecord record) {
        List<HealthWarning> warnings = new ArrayList<>();
        
        checkBloodPressure(record, warnings);
        checkBloodSugar(record, warnings);
        checkTemperature(record, warnings);
        checkHeartRate(record, warnings);
        
        return warnings;
    }
    
    private void checkBloodPressure(HealthRecord record, List<HealthWarning> warnings) {
        HealthWarningProperties.BloodPressure config = warningProperties.getBloodPressure();
        boolean isHigh = record.getBloodPressureHigh() != null && 
                         record.getBloodPressureHigh().compareTo(config.getHighThreshold()) >= 0;
        boolean isLow = record.getBloodPressureLow() != null && 
                        record.getBloodPressureLow().compareTo(config.getLowThreshold()) >= 0;
        
        if (isHigh || isLow) {
            HealthWarning warning = createWarning(record);
            warning.setWarningType(config.getWarningType());
            warning.setWarningLevel(config.getWarningLevel());
            
            StringBuilder indicators = new StringBuilder();
            if (isHigh) {
                indicators.append("收缩压: ").append(record.getBloodPressureHigh());
            }
            if (isLow) {
                if (indicators.length() > 0) indicators.append(", ");
                indicators.append("舒张压: ").append(record.getBloodPressureLow());
            }
            warning.setAbnormalIndicators(indicators.toString());
            warnings.add(warning);
        }
    }
    
    private void checkBloodSugar(HealthRecord record, List<HealthWarning> warnings) {
        HealthWarningProperties.BloodSugar config = warningProperties.getBloodSugar();
        if (record.getBloodSugar() != null && 
            record.getBloodSugar().compareTo(config.getHighThreshold()) >= 0) {
            HealthWarning warning = createWarning(record);
            warning.setWarningType(config.getWarningType());
            warning.setWarningLevel(config.getWarningLevel());
            warning.setAbnormalIndicators("血糖: " + record.getBloodSugar());
            warnings.add(warning);
        }
    }
    
    private void checkTemperature(HealthRecord record, List<HealthWarning> warnings) {
        HealthWarningProperties.Temperature config = warningProperties.getTemperature();
        if (record.getTemperature() != null && 
            record.getTemperature().compareTo(config.getHighThreshold()) >= 0) {
            HealthWarning warning = createWarning(record);
            warning.setWarningType(config.getWarningType());
            warning.setWarningLevel(config.getWarningLevel());
            warning.setAbnormalIndicators("体温: " + record.getTemperature());
            warnings.add(warning);
        }
    }
    
    private void checkHeartRate(HealthRecord record, List<HealthWarning> warnings) {
        HealthWarningProperties.HeartRate config = warningProperties.getHeartRate();
        boolean isHigh = record.getHeartRate() != null && 
                         record.getHeartRate().compareTo(config.getHighThreshold()) >= 0;
        boolean isLow = record.getHeartRate() != null && 
                        record.getHeartRate().compareTo(config.getLowThreshold()) <= 0;
        
        if (isHigh || isLow) {
            HealthWarning warning = createWarning(record);
            warning.setWarningType(config.getWarningType());
            warning.setWarningLevel(config.getWarningLevel());
            
            String indicator = isHigh ? 
                "心率过高: " + record.getHeartRate() : 
                "心率过低: " + record.getHeartRate();
            warning.setAbnormalIndicators(indicator);
            warnings.add(warning);
        }
    }
    
    private HealthWarning createWarning(HealthRecord record) {
        HealthWarning warning = new HealthWarning();
        warning.setElderId(record.getElderId());
        warning.setRecordId(record.getId());
        warning.setStatus(0);
        return warning;
    }
}
