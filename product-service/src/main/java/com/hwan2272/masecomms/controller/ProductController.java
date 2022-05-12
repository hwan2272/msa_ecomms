package com.hwan2272.masecomms.controller;

import com.hwan2272.masecomms.dto.ProductDto;
import com.hwan2272.masecomms.service.ProductService;
import com.hwan2272.masecomms.vo.RequestProduct;
import com.hwan2272.masecomms.vo.ResponseProduct;
import com.netflix.discovery.converters.Auto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ModelMapper mMapper;

    @Autowired
    ProductService productService;

    @Value("${message.welcome}")
    String welcome;

    @Value("${message.health}")
    String health;

    @GetMapping("/hello")
    public String hello() {
        return welcome;
    }

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return String.format(health, "ProductService");
    }

    @GetMapping("/{productId}")
    public ResponseEntity getProduct(
            @PathVariable(value = "productId") String productId) {
        ProductDto productDto = productService.getProduct(productId);
        ResponseProduct product = mMapper.map(productDto, ResponseProduct.class);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

}
