package com.hwan2272.msaecomms.controller;

import com.hwan2272.msaecomms.dto.OrderDto;
import com.hwan2272.msaecomms.entity.OrderEntity;
import com.hwan2272.msaecomms.kafka.KafkaProducer;
import com.hwan2272.msaecomms.service.OrderService;
import com.hwan2272.msaecomms.vo.RequestOrder;
import com.hwan2272.msaecomms.vo.ResponseOrder;
import com.netflix.discovery.converters.Auto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    ModelMapper mMapper;

    @Autowired
    OrderService orderService;

    @Autowired
    KafkaProducer kafkaProducer;

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

    @PostMapping("/{userId}")
    public ResponseEntity addOrderInfo(
            @PathVariable String userId,
            @RequestBody RequestOrder requestOrder) {
        OrderDto orderDto = mMapper.map(requestOrder, OrderDto.class);
        orderDto.setUserId(userId);
        orderService.addOrder(orderDto);

        ResponseOrder responseOrder = mMapper.map(orderDto, ResponseOrder.class);

        kafkaProducer.send("topic-orders", orderDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity getOrders(
            @PathVariable(value = "userId") String userId) {
        List<OrderDto> orders = orderService.getUserOrders(userId.trim());
        return ResponseEntity.status(HttpStatus.OK)
                .body(orders);
    }

}
