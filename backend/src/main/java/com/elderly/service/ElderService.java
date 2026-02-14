package com.elderly.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elderly.entity.Elder;
import com.elderly.mapper.ElderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.StrUtil;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ElderService {
    private final ElderMapper elderMapper;

    public IPage<Elder> page(int current, int size, String keyword) {
        LambdaQueryWrapper<Elder> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(Elder::getName, keyword)
                    .or().like(Elder::getPhone, keyword);
        }
        wrapper.orderByDesc(Elder::getCreateTime);
        return elderMapper.selectPage(new Page<>(current, size), wrapper);
    }

    public Elder getById(Long id) {
        return elderMapper.selectById(id);
    }

    public List<Elder> list() {
        return elderMapper.selectList(new LambdaQueryWrapper<Elder>()
                .eq(Elder::getStatus, 1)
                .orderByDesc(Elder::getCreateTime));
    }

    public void save(Elder elder) {
        elderMapper.insert(elder);
    }

    public void update(Elder elder) {
        elderMapper.updateById(elder);
    }

    public void delete(Long id) {
        elderMapper.deleteById(id);
    }

    public long count() {
        return elderMapper.selectCount(new LambdaQueryWrapper<Elder>().eq(Elder::getStatus, 1));
    }
}
