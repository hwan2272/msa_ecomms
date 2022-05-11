package com.hwan2272.msaecomms.service;

import com.hwan2272.msaecomms.dto.OrderDto;
import com.hwan2272.msaecomms.entity.OrderEntity;
import com.hwan2272.msaecomms.repository.OrderDataJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    ModelMapper mMapper;

    @Autowired
    OrderDataJpaRepository orderDataJpaRepository;

    public void addOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getUnitPrice() * orderDto.getQty());
        OrderEntity orderEntity = mMapper.map(orderDto, OrderEntity.class);
        orderDataJpaRepository.save(orderEntity);
    }

    public List<OrderDto> getUserOrders(String userId) {
        List<OrderDto> ordersList = new ArrayList<>();
        Iterable<OrderEntity> orderEntities = orderDataJpaRepository.findOrdersByUserId(userId);
        orderEntities.forEach(v -> {
            ordersList.add(mMapper.map(v, OrderDto.class));
        });
        return ordersList;
    }
}
