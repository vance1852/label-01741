package com.elderly.strategy;

import com.elderly.entity.HealthRecord;
import com.elderly.entity.HealthWarning;

import java.util.List;

public interface HealthWarningStrategy {
    List<HealthWarning> check(HealthRecord record);
}
