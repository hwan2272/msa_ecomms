package com.hwan2272.msaecomms.entity;

import com.hwan2272.msaecomms.repository.OrderDataJpaRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
class OrderEntityTest {

    @Autowired
    OrderDataJpaRepository orderDataJpaRepository;

    @Test
    public void testEntity() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(UUID.randomUUID().toString());
        orderEntity.setProductId(UUID.randomUUID().toString());
        orderEntity.setOrderId(UUID.randomUUID().toString());
        orderEntity.setUnitPrice(400);
        orderEntity.setQty(10);
        orderEntity.setTotalPrice(orderEntity.getUnitPrice() * orderEntity.getQty());
        orderEntity.setCreatedAt(new Date());

        orderDataJpaRepository.save(orderEntity);
    }

}