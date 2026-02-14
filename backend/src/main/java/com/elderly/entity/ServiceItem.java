package com.elderly.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("service_item")
public class ServiceItem {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @NotBlank(message = "服务名称不能为空")
    @Size(max = 100, message = "服务名称长度不能超过100个字符")
    private String name;
    
    @Size(max = 500, message = "服务描述长度不能超过500个字符")
    private String description;
    
    @NotNull(message = "服务价格不能为空")
    @DecimalMin(value = "0.00", message = "服务价格不能为负数")
    @DecimalMax(value = "99999.99", message = "服务价格超出范围")
    private BigDecimal price;
    
    @NotNull(message = "服务时长不能为空")
    @Min(value = 1, message = "服务时长至少为1分钟")
    @Max(value = 1440, message = "服务时长不能超过24小时")
    private Integer duration;
    
    @Min(value = 0, message = "状态值无效")
    @Max(value = 1, message = "状态值无效")
    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
}
