package com.hwan2272.masecomms.repository;

import com.hwan2272.masecomms.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDataJpaRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByProductId(String productId);
}
