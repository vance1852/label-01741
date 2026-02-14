package com.elderly.controller;

import com.elderly.common.PageResult;
import com.elderly.common.Result;
import com.elderly.entity.ServiceOrder;
import com.elderly.service.ServiceOrderService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class ServiceOrderController {
    private final ServiceOrderService serviceOrderService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") int current,
                          @RequestParam(defaultValue = "10") int size) {
        return Result.success(PageResult.of(serviceOrderService.page(current, size)));
    }

    @PostMapping
    public Result<?> save(@Valid @RequestBody ServiceOrder order) {
        if (order.getElderId() == null) {
            return Result.error("请选择老人");
        }
        if (order.getServiceId() == null) {
            return Result.error("请选择服务项目");
        }
        serviceOrderService.save(order);
        return Result.success();
    }

    @Data
    public static class StatusDTO {
        private Long id;
        private Integer status;
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody StatusDTO dto) {
        if (dto.getId() == null || dto.getStatus() == null) {
            return Result.error("参数不完整");
        }
        serviceOrderService.updateStatus(dto.getId(), dto.getStatus());
        return Result.success();
    }
}
