package com.hwan2272.msaecomms.repository;

import com.hwan2272.msaecomms.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDataJpaRepository extends JpaRepository<OrderEntity, Long> {
    public Iterable<OrderEntity> findOrdersByUserId(String userId);
    public OrderEntity findByOrderId(String orderId);
}
