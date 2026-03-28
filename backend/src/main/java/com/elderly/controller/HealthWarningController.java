package com.elderly.controller;

import com.elderly.common.PageResult;
import com.elderly.common.Result;
import com.elderly.service.HealthWarningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warnings")
@RequiredArgsConstructor
public class HealthWarningController {
    private final HealthWarningService healthWarningService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") int current,
                         @RequestParam(defaultValue = "10") int size) {
        return Result.success(PageResult.of(healthWarningService.page(current, size)));
    }

    @GetMapping("/active")
    public Result<?> getActiveWarnings() {
        return Result.success(healthWarningService.getActiveWarnings());
    }

    @PostMapping("/{id}/handle")
    public Result<?> handleWarning(@PathVariable Long id) {
        healthWarningService.handleWarning(id);
        return Result.success();
    }
}
