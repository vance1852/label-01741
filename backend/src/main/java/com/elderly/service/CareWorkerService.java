package com.elderly.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elderly.entity.CareWorker;
import com.elderly.mapper.CareWorkerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.StrUtil;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CareWorkerService {
    private final CareWorkerMapper careWorkerMapper;

    public IPage<CareWorker> page(int current, int size, String keyword) {
        LambdaQueryWrapper<CareWorker> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(CareWorker::getName, keyword)
                    .or().like(CareWorker::getPhone, keyword);
        }
        wrapper.orderByDesc(CareWorker::getCreateTime);
        return careWorkerMapper.selectPage(new Page<>(current, size), wrapper);
    }

    public CareWorker getById(Long id) {
        return careWorkerMapper.selectById(id);
    }

    public List<CareWorker> list() {
        return careWorkerMapper.selectList(new LambdaQueryWrapper<CareWorker>()
                .eq(CareWorker::getStatus, 1)
                .orderByDesc(CareWorker::getCreateTime));
    }

    public void save(CareWorker worker) {
        careWorkerMapper.insert(worker);
    }

    public void update(CareWorker worker) {
        careWorkerMapper.updateById(worker);
    }

    public void delete(Long id) {
        careWorkerMapper.deleteById(id);
    }

    public long count() {
        return careWorkerMapper.selectCount(new LambdaQueryWrapper<CareWorker>().eq(CareWorker::getStatus, 1));
    }
}
