package com.elderly.config;

import com.elderly.entity.HealthRecord;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class HealthWarningConfig {

    public static final BigDecimal SBP_THRESHOLD = new BigDecimal("140");
    public static final BigDecimal DBP_THRESHOLD = new BigDecimal("90");
    public static final BigDecimal BLOOD_SUGAR_THRESHOLD = new BigDecimal("7.0");
    public static final BigDecimal TEMPERATURE_THRESHOLD = new BigDecimal("37.3");
    public static final BigDecimal HR_HIGH_THRESHOLD = new BigDecimal("100");
    public static final BigDecimal HR_LOW_THRESHOLD = new BigDecimal("50");

    public List<WarningRule> getWarningRules() {
        List<WarningRule> rules = new ArrayList<>();
        
        rules.add(new WarningRule(
            "高血压",
            "high",
            record -> record.getBloodPressureHigh() != null && record.getBloodPressureHigh().compareTo(SBP_THRESHOLD) >= 0
                      || record.getBloodPressureLow() != null && record.getBloodPressureLow().compareTo(DBP_THRESHOLD) >= 0
        ));
        
        rules.add(new WarningRule(
            "高血糖",
            "medium",
            record -> record.getBloodSugar() != null && record.getBloodSugar().compareTo(BLOOD_SUGAR_THRESHOLD) >= 0
        ));
        
        rules.add(new WarningRule(
            "发热",
            "medium",
            record -> record.getTemperature() != null && record.getTemperature().compareTo(TEMPERATURE_THRESHOLD) >= 0
        ));
        
        rules.add(new WarningRule(
            "心率异常",
            "medium",
            record -> record.getHeartRate() != null && 
                      (record.getHeartRate().compareTo(HR_HIGH_THRESHOLD) >= 0 || 
                       record.getHeartRate().compareTo(HR_LOW_THRESHOLD) <= 0)
        ));
        
        return rules;
    }

    @FunctionalInterface
    public interface WarningCondition {
        boolean isTriggered(HealthRecord record);
    }

    public static class WarningRule {
        private String indicator;
        private String level;
        private WarningCondition condition;

        public WarningRule(String indicator, String level, WarningCondition condition) {
            this.indicator = indicator;
            this.level = level;
            this.condition = condition;
        }

        public String getIndicator() {
            return indicator;
        }

        public String getLevel() {
            return level;
        }

        public WarningCondition getCondition() {
            return condition;
        }
    }
}
