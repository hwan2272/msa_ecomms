package com.hwan2272.msaecomms.entity;

import com.hwan2272.msaecomms.repository.OrderDataJpaRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;


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

    @Test
    public void testEntity2() {
        String userId= "user1111";
        String productId= "P-001";
        Iterable<OrderEntity> orders = orderDataJpaRepository.findOrders2Condition(userId, productId);
        System.out.println(String.format("orders=%s", orders.toString()));
    }

    @Test
    public void testEntity3() {
        String userId= "user1111";
        List<String> userIds = new ArrayList<>();
        userIds.add(userId);

        Iterable<OrderEntity> orders = orderDataJpaRepository.findOrders1CollectCondition(userIds);
        System.out.println(String.format("orders=%s", orders.toString()));
    }

    //@Test
    //public void testEntity4() {
        //return type optional = 단건, 값이 있을지 없을지 모른다
        //기타: url참조 = https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-return-types
    //}

    @Test
    public void testEntity5Paging() {
        int page = 1;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<OrderEntity> page1 = orderDataJpaRepository.findOrdersPageByUserId("user1111", pageRequest);

        List<OrderEntity> content = page1.getContent();
        long totalElements = page1.getTotalElements();

        //System.out.println("content = " + content);
        assertThat(page1.getTotalElements() == 3);
        assertThat(page1.getContent().get(0).getUserId().equals("user1111"));
    }

}