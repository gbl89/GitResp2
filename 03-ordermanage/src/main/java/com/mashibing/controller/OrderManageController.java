package com.mashibing.controller;

import com.mashibing.service.TbOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderManageController {

    @Autowired
    private TbOrderService OrderService;

    @GetMapping("/create")
    public void create() {
        OrderService.save();
        log.info("创建订单成功");
    }
}
