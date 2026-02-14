package com.elderly.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("health_record")
public class HealthRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @NotNull(message = "老人ID不能为空")
    private Long elderId;
    
    @DecimalMin(value = "60.0", message = "收缩压值异常，正常范围60-250")
    @DecimalMax(value = "250.0", message = "收缩压值异常，正常范围60-250")
    private BigDecimal bloodPressureHigh;
    
    @DecimalMin(value = "40.0", message = "舒张压值异常，正常范围40-150")
    @DecimalMax(value = "150.0", message = "舒张压值异常，正常范围40-150")
    private BigDecimal bloodPressureLow;
    
    @DecimalMin(value = "30.0", message = "心率值异常，正常范围30-200")
    @DecimalMax(value = "200.0", message = "心率值异常，正常范围30-200")
    private BigDecimal heartRate;
    
    @DecimalMin(value = "1.0", message = "血糖值异常，正常范围1-30")
    @DecimalMax(value = "30.0", message = "血糖值异常，正常范围1-30")
    private BigDecimal bloodSugar;
    
    @DecimalMin(value = "34.0", message = "体温值异常，正常范围34-42")
    @DecimalMax(value = "42.0", message = "体温值异常，正常范围34-42")
    private BigDecimal temperature;
    
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recordTime;
    
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @TableLogic
    private Integer deleted;
    
    @TableField(exist = false)
    private String elderName;
}
