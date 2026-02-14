package com.elderly.controller;

import com.elderly.common.PageResult;
import com.elderly.common.Result;
import com.elderly.entity.Elder;
import com.elderly.service.ElderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/elder")
@RequiredArgsConstructor
public class ElderController {
    private final ElderService elderService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") int current,
                          @RequestParam(defaultValue = "10") int size,
                          @RequestParam(required = false) String keyword) {
        return Result.success(PageResult.of(elderService.page(current, size, keyword)));
    }

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(elderService.list());
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(elderService.getById(id));
    }

    @PostMapping
    public Result<?> save(@Valid @RequestBody Elder elder) {
        elderService.save(elder);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@Valid @RequestBody Elder elder) {
        if (elder.getId() == null) {
            return Result.badRequest("ID不能为空");
        }
        elderService.update(elder);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        elderService.delete(id);
        return Result.success();
    }
}
