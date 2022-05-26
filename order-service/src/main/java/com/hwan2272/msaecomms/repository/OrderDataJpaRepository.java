package com.hwan2272.msaecomms.repository;

import com.hwan2272.msaecomms.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OrderDataJpaRepository extends JpaRepository<OrderEntity, Long> {
    public Iterable<OrderEntity> findOrdersByUserId(String userId);
    public OrderEntity findByOrderId(String orderId);

    @Query("select o from OrderEntity o where o.userId= :userId and o.productId= :productId")
    public Iterable<OrderEntity> findOrders2Condition(
            @Param("userId") String userId, @Param("productId") String productId);

    @Query("select o from OrderEntity o where o.userId in :userIds")
    public Iterable<OrderEntity> findOrders1CollectCondition(
            @Param("userIds") Collection<String> userIds);

    Page<OrderEntity> findOrdersPageByUserId(String userId, Pageable pageable);
    Slice<OrderEntity> findOrdersSliceByUserId(String userId, Pageable pageable);
}
