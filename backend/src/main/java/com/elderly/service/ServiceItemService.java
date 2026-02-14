package com.elderly.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elderly.entity.ServiceItem;
import com.elderly.mapper.ServiceItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.StrUtil;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceItemService {
    private final ServiceItemMapper serviceItemMapper;

    public IPage<ServiceItem> page(int current, int size, String keyword) {
        LambdaQueryWrapper<ServiceItem> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(ServiceItem::getName, keyword);
        }
        wrapper.orderByDesc(ServiceItem::getCreateTime);
        return serviceItemMapper.selectPage(new Page<>(current, size), wrapper);
    }

    public List<ServiceItem> list() {
        return serviceItemMapper.selectList(new LambdaQueryWrapper<ServiceItem>()
                .eq(ServiceItem::getStatus, 1)
                .orderByDesc(ServiceItem::getCreateTime));
    }

    public void save(ServiceItem item) {
        serviceItemMapper.insert(item);
    }

    public void update(ServiceItem item) {
        serviceItemMapper.updateById(item);
    }

    public void delete(Long id) {
        serviceItemMapper.deleteById(id);
    }
}
