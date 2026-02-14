package com.elderly.controller;

import com.elderly.common.PageResult;
import com.elderly.common.Result;
import com.elderly.entity.ServiceItem;
import com.elderly.service.ServiceItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceItemService serviceItemService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") int current,
                          @RequestParam(defaultValue = "10") int size,
                          @RequestParam(required = false) String keyword) {
        return Result.success(PageResult.of(serviceItemService.page(current, size, keyword)));
    }

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(serviceItemService.list());
    }

    @PostMapping
    public Result<?> save(@Valid @RequestBody ServiceItem item) {
        if (item.getName() == null || item.getName().isBlank()) {
            return Result.error("服务名称不能为空");
        }
        serviceItemService.save(item);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@Valid @RequestBody ServiceItem item) {
        if (item.getId() == null) {
            return Result.error("ID不能为空");
        }
        serviceItemService.update(item);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        serviceItemService.delete(id);
        return Result.success();
    }
}
