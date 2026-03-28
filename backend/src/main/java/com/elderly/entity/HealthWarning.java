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
    
    private Long healthRecordId;
    
    private String indicator;
    
    private String warningLevel;
    
    private String message;
    
    @TableLogic
    private Integer deleted;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @TableField(exist = false)
    private String elderName;
}
