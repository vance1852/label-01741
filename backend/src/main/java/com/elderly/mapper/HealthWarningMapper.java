package com.elderly.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elderly.entity.HealthWarning;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface HealthWarningMapper extends BaseMapper<HealthWarning> {
    
    @Select("SELECT w.*, e.name as elderName FROM health_warning w " +
            "LEFT JOIN elder e ON w.elder_id = e.id " +
            "WHERE w.deleted = 0 " +
            "ORDER BY w.create_time DESC " +
            "LIMIT 10")
    List<HealthWarning> selectRecentWarningsWithElderName();
}
