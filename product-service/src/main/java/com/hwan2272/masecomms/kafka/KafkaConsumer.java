package com.hwan2272.masecomms.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwan2272.masecomms.entity.ProductEntity;
import com.hwan2272.masecomms.repository.ProductDataJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KafkaConsumer {
    @Autowired
    ProductDataJpaRepository productDataJpaRepository;

    @KafkaListener(topics = "topic-orders")
    public void updateUnitStock(String kafkaMessage) {
        log.info("Kafka Message : ->" + kafkaMessage);

        Map<String, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<String, Object>>() {});
        }
        catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        ProductEntity product = productDataJpaRepository.findByProductId((String) map.get("productId"));
        if(product != null) {
            product.setUnitStock(product.getUnitStock() - (Integer)map.get("qty"));
            productDataJpaRepository.save(product);
        }
    }
}
