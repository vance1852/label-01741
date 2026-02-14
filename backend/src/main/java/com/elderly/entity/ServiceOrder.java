package com.elderly.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("service_order")
public class ServiceOrder {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String orderNo;
    
    @NotNull(message = "请选择老人")
    private Long elderId;
    
    @NotNull(message = "请选择服务项目")
    private Long serviceId;
    
    private Long workerId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @FutureOrPresent(message = "预约时间不能是过去的时间")
    private LocalDateTime appointmentTime;
    
    @Min(value = 0, message = "状态值无效")
    @Max(value = 3, message = "状态值无效")
    private Integer status;
    
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
    
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
    @TableField(exist = false)
    private String serviceName;
    @TableField(exist = false)
    private String workerName;
}
