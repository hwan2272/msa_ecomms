package com.hwan2272.msaecomms.controller;

import com.hwan2272.msaecomms.dto.KafkaConnectOrderDtoV1;
import com.hwan2272.msaecomms.dto.KafkaConnectOrderDtoV2;
import com.hwan2272.msaecomms.dto.OrderDto;
import com.hwan2272.msaecomms.entity.OrderEntity;
import com.hwan2272.msaecomms.kafka.KafkaConnectProducer;
import com.hwan2272.msaecomms.kafka.KafkaProducer;
import com.hwan2272.msaecomms.service.OrderService;
import com.hwan2272.msaecomms.vo.RequestOrder;
import com.hwan2272.msaecomms.vo.ResponseOrder;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    @Autowired
    ModelMapper mMapper;

    @Autowired
    OrderService orderService;

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    KafkaConnectProducer kafkaConnectProducer;

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
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getUnitPrice() * orderDto.getQty());
        orderDto.setCreatedAt(new Date());
        //orderService.addOrder(orderDto);//kafka connect로 변경

        //ResponseOrder responseOrder = mMapper.map(orderDto, ResponseOrder.class);//kafka connect로 변경

        KafkaConnectOrderDtoV2 responseKafkaConnectOrderDto
                = kafkaConnectProducer.send("orders", orderDto); //kafka connect 적재
        kafkaProducer.send("topic-orders", orderDto); //kafka consumer적재(product 처리용)

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseKafkaConnectOrderDto);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity getOrders(
            @PathVariable(value = "userId") String userId) {
        log.info(":::::::::::::::::::Before retrieve order data");
        List<OrderDto> orders = orderService.getUserOrders(userId.trim());
        log.info(":::::::::::::::::::After retrieve order data");
        return ResponseEntity.status(HttpStatus.OK)
                .body(orders);
    }

}
