package com.elderly.controller;

import com.elderly.common.PageResult;
import com.elderly.common.Result;
import com.elderly.entity.CareWorker;
import com.elderly.service.CareWorkerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/worker")
@RequiredArgsConstructor
public class CareWorkerController {
    private final CareWorkerService careWorkerService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") int current,
                          @RequestParam(defaultValue = "10") int size,
                          @RequestParam(required = false) String keyword) {
        return Result.success(PageResult.of(careWorkerService.page(current, size, keyword)));
    }

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(careWorkerService.list());
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(careWorkerService.getById(id));
    }

    @PostMapping
    public Result<?> save(@Valid @RequestBody CareWorker worker) {
        if (worker.getName() == null || worker.getName().isBlank()) {
            return Result.error("姓名不能为空");
        }
        careWorkerService.save(worker);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@Valid @RequestBody CareWorker worker) {
        if (worker.getId() == null) {
            return Result.error("ID不能为空");
        }
        careWorkerService.update(worker);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        careWorkerService.delete(id);
        return Result.success();
    }
}
