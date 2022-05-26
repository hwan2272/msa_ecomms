package com.hwan2272.msaecomms.repository;

import com.hwan2272.msaecomms.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;

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

    //Slice<OrderEntity> findOrdersSliceByUserId(String userId, Pageable pageable);

    //fetchJoin - N+1 문제 솔루션
    @Query("select o from OrderEntity o inner join fetch o.user")
    public OrderEntity findOrdersFetchJoin();

    //fetchJoin - EntityGraph - Data Jpa용
    @Override
    @EntityGraph(attributePaths = {"user"})
    public List<OrderEntity> findAll();

    @EntityGraph(attributePaths = {"user"})
    @Query("select o from OrderEntity o inner join fetch o.user")
    public OrderEntity findOrdersEntityGraph();

    //readOnly로만 작동
    @QueryHints(value = @QueryHint(name= "org.hibernate.readOnly", value = "true"))
    public OrderEntity findOrderReadOnly();



}
