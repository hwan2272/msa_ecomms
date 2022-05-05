package com.hwan2272.msaecomms.controller;

import com.hwan2272.msaecomms.service.OrderService;
import com.hwan2272.msaecomms.vo.RequestOrder;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Value("${message.welcome}")
    String welcome;

    @Value("${message.health}")
    String health;

    @GetMapping("/hello")
    public String hello() {
        return welcome;
    }

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return String.format(health, "OrderService");
    }

    @GetMapping("/{userId}")
    public RequestOrder getOrders(@PathVariable(value = "userId") String userId) {
        return null;
    }

    @PostMapping
    public void addOrder(@RequestBody RequestOrder requestOrder) {

    }
}
