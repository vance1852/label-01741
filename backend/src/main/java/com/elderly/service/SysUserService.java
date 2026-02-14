package com.elderly.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elderly.entity.SysUser;
import com.elderly.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.StrUtil;

@Service
@RequiredArgsConstructor
public class SysUserService {
    private final SysUserMapper sysUserMapper;

    public SysUser findByUsername(String username) {
        return sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username));
    }

    public IPage<SysUser> page(int current, int size, String keyword) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(SysUser::getUsername, keyword)
                    .or().like(SysUser::getRealName, keyword);
        }
        wrapper.orderByDesc(SysUser::getCreateTime);
        return sysUserMapper.selectPage(new Page<>(current, size), wrapper);
    }

    public void save(SysUser user) {
        sysUserMapper.insert(user);
    }

    public void update(SysUser user) {
        sysUserMapper.updateById(user);
    }

    public void delete(Long id) {
        sysUserMapper.deleteById(id);
    }
}
