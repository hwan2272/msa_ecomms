package com.hwan2272.msaecomms.controller;

import com.hwan2272.msaecomms.dto.KafkaConnectOrderDto;
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

    private final String sinkTopicOrdersConnectName = "sink-topic-orders-connect";

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
            @RequestBody RequestOrder requestOrder) throws Exception {
        OrderDto orderDto = mMapper.map(requestOrder, OrderDto.class);
        orderDto.setUserId(userId);
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getUnitPrice() * orderDto.getQty());
        orderDto.setCreatedAt(new Date());
        //orderService.addOrder(orderDto);//kafka connect로 변경

        //ResponseOrder responseOrder = mMapper.map(orderDto, ResponseOrder.class);//kafka connect로 변경

        KafkaConnectOrderDto responseKafkaConnectOrderDto
                = kafkaConnectProducer.send(sinkTopicOrdersConnectName, orderDto);
        //kafkaProducer.send("topic-orders", orderDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseKafkaConnectOrderDto);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity getOrders(
            @PathVariable(value = "userId") String userId) {
        List<OrderDto> orders = orderService.getUserOrders(userId.trim());
        return ResponseEntity.status(HttpStatus.OK)
                .body(orders);
    }

}
