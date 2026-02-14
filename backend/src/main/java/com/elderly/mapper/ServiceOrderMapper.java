package com.elderly.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.elderly.entity.ServiceOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ServiceOrderMapper extends BaseMapper<ServiceOrder> {
    @Select("SELECT o.*, e.name as elder_name, s.name as service_name, w.name as worker_name " +
            "FROM service_order o " +
            "LEFT JOIN elder e ON o.elder_id = e.id " +
            "LEFT JOIN service_item s ON o.service_id = s.id " +
            "LEFT JOIN care_worker w ON o.worker_id = w.id " +
            "WHERE o.deleted = 0 " +
            "ORDER BY o.create_time DESC")
    IPage<ServiceOrder> selectPageWithDetails(IPage<ServiceOrder> page);
}
