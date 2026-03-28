package com.elderly.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Component
@ConfigurationProperties(prefix = "health.warning")
public class HealthWarningProperties {
    
    private BloodPressure bloodPressure = new BloodPressure();
    private BloodSugar bloodSugar = new BloodSugar();
    private Temperature temperature = new Temperature();
    private HeartRate heartRate = new HeartRate();
    
    @Data
    public static class BloodPressure {
        private BigDecimal highThreshold = new BigDecimal("140.0");
        private BigDecimal lowThreshold = new BigDecimal("90.0");
        private Integer warningLevel = 2;
        private String warningType = "HIGH_BLOOD_PRESSURE";
    }
    
    @Data
    public static class BloodSugar {
        private BigDecimal highThreshold = new BigDecimal("7.0");
        private Integer warningLevel = 2;
        private String warningType = "HIGH_BLOOD_SUGAR";
    }
    
    @Data
    public static class Temperature {
        private BigDecimal highThreshold = new BigDecimal("37.3");
        private Integer warningLevel = 1;
        private String warningType = "HIGH_TEMPERATURE";
    }
    
    @Data
    public static class HeartRate {
        private BigDecimal highThreshold = new BigDecimal("100.0");
        private BigDecimal lowThreshold = new BigDecimal("50.0");
        private Integer warningLevel = 2;
        private String warningType = "HEART_RATE";
    }
}
