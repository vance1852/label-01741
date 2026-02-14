package com.elderly.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elderly.entity.SysLog;
import com.elderly.mapper.SysLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysLogService {
    private final SysLogMapper sysLogMapper;

    public void save(SysLog log) {
        sysLogMapper.insert(log);
    }

    public IPage<SysLog> page(int current, int size) {
        return sysLogMapper.selectPage(new Page<>(current, size),
                new LambdaQueryWrapper<SysLog>().orderByDesc(SysLog::getCreateTime));
    }
}
