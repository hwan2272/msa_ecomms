package com.hwan2272.masecomms.controller;

import com.hwan2272.masecomms.service.ProductService;
import com.hwan2272.masecomms.vo.RequestProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService ProductService;

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
    public RequestProduct getProduct(@PathVariable(value = "productId") String productId) {
        return null;
    }

    @PostMapping
    public void addProduct(@RequestBody RequestProduct requestProduct) {

    }
}
