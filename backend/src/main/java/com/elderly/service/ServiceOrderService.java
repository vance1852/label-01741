package com.elderly.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elderly.entity.ServiceOrder;
import com.elderly.mapper.ServiceOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.IdUtil;

@Service
@RequiredArgsConstructor
public class ServiceOrderService {
    private final ServiceOrderMapper serviceOrderMapper;

    public IPage<ServiceOrder> page(int current, int size) {
        return serviceOrderMapper.selectPageWithDetails(new Page<>(current, size));
    }

    public void save(ServiceOrder order) {
        order.setOrderNo(IdUtil.getSnowflakeNextIdStr());
        order.setStatus(0);
        serviceOrderMapper.insert(order);
    }

    public void updateStatus(Long id, Integer status) {
        ServiceOrder order = new ServiceOrder();
        order.setId(id);
        order.setStatus(status);
        serviceOrderMapper.updateById(order);
    }

    public long countByStatus(Integer status) {
        return serviceOrderMapper.selectCount(new LambdaQueryWrapper<ServiceOrder>()
                .eq(status != null, ServiceOrder::getStatus, status));
    }
}
