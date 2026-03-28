package com.elderly.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("health_warning")
public class HealthWarning {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long elderId;
    
    private Long recordId;
    
    private String warningType;
    
    private Integer warningLevel;
    
    private String abnormalIndicators;
    
    private Integer status;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime handledTime;
    
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
    
    @TableField(exist = false)
    private String elderName;
}
