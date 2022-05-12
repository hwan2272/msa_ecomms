package com.hwan2272.masecomms.service;

import com.hwan2272.masecomms.dto.ProductDto;
import com.hwan2272.masecomms.entity.ProductEntity;
import com.hwan2272.masecomms.repository.ProductDataJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ModelMapper mMapper;

    @Autowired
    ProductDataJpaRepository productDataJpaRepository;

    public ProductDto getProduct(String productId) {
        ProductEntity productEntity = productDataJpaRepository.findByProductId(productId);
        ProductDto productDto = mMapper.map(productEntity, ProductDto.class);
        return productDto;
    }
}
