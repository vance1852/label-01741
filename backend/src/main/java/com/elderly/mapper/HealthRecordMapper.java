package com.elderly.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.elderly.entity.HealthRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HealthRecordMapper extends BaseMapper<HealthRecord> {
    @Select("SELECT h.*, e.name as elder_name FROM health_record h " +
            "LEFT JOIN elder e ON h.elder_id = e.id " +
            "WHERE h.deleted = 0 ORDER BY h.record_time DESC")
    IPage<HealthRecord> selectPageWithElder(IPage<HealthRecord> page);
}
