package com.elderly.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.elderly.entity.HealthWarning;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HealthWarningMapper extends BaseMapper<HealthWarning> {
    
    @Select("SELECT w.*, e.name as elder_name FROM health_warning w " +
            "LEFT JOIN elder e ON w.elder_id = e.id " +
            "WHERE w.deleted = 0 AND w.status = #{status} " +
            "ORDER BY w.create_time DESC")
    List<HealthWarning> selectByStatusWithElder(@Param("status") Integer status);
    
    @Select("SELECT w.*, e.name as elder_name FROM health_warning w " +
            "LEFT JOIN elder e ON w.elder_id = e.id " +
            "WHERE w.deleted = 0 ORDER BY w.create_time DESC")
    IPage<HealthWarning> selectPageWithElder(IPage<HealthWarning> page);
}
