package com.hwan2272.masecomms.entity;

import com.hwan2272.masecomms.repository.ProductDataJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
class ProductEntityTest {

    @Autowired
    ProductDataJpaRepository productDataJpaRepository;

    //@Test
    public void testEntity() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId("P-001-T");
        productEntity.setProductName("P-001-T");
        productEntity.setNote("1번상품 테스트");
        productEntity.setUnitStock(5000);
        productEntity.setUnitPrice(5000);
        productEntity.setCreatedAt(new Date());
        productDataJpaRepository.save(productEntity);

    }
}