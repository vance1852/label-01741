package com.elderly.controller;

import com.elderly.common.PageResult;
import com.elderly.common.Result;
import com.elderly.service.SysLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/log")
@RequiredArgsConstructor
public class SysLogController {
    private final SysLogService sysLogService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") int current,
                          @RequestParam(defaultValue = "10") int size) {
        return Result.success(PageResult.of(sysLogService.page(current, size)));
    }
}
