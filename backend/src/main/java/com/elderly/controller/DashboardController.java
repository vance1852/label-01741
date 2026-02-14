package com.elderly.controller;

import com.elderly.common.Result;
import com.elderly.service.CareWorkerService;
import com.elderly.service.ElderService;
import com.elderly.service.ServiceOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final ElderService elderService;
    private final CareWorkerService careWorkerService;
    private final ServiceOrderService serviceOrderService;

    @GetMapping("/stats")
    public Result<?> stats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("elderCount", elderService.count());
        stats.put("workerCount", careWorkerService.count());
        stats.put("pendingOrders", serviceOrderService.countByStatus(0));
        stats.put("completedOrders", serviceOrderService.countByStatus(2));
        return Result.success(stats);
    }
}
