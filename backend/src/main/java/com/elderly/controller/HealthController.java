package com.elderly.controller;

import com.elderly.common.PageResult;
import com.elderly.common.Result;
import com.elderly.entity.HealthRecord;
import com.elderly.service.HealthRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthController {
    private final HealthRecordService healthRecordService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") int current,
                          @RequestParam(defaultValue = "10") int size) {
        return Result.success(PageResult.of(healthRecordService.page(current, size)));
    }

    @GetMapping("/elder/{elderId}")
    public Result<?> listByElderId(@PathVariable Long elderId) {
        return Result.success(healthRecordService.listByElderId(elderId));
    }

    @PostMapping
    public Result<?> save(@Valid @RequestBody HealthRecord record) {
        if (record.getElderId() == null) {
            return Result.error("请选择老人");
        }
        healthRecordService.save(record);
        return Result.success();
    }
}
