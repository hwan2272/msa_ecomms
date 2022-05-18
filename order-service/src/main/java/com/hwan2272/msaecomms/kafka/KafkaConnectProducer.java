package com.hwan2272.msaecomms.kafka;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.hwan2272.msaecomms.dto.KafkaConnectOrderDto;
import com.hwan2272.msaecomms.dto.KafkaConnectOrderDtoV2;
import com.hwan2272.msaecomms.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConnectProducer {
    @Autowired
    KafkaTemplate kafkaTemplate;

    public KafkaConnectOrderDto send(String topic, OrderDto orderDto) throws Exception {

        KafkaConnectOrderDto kafkaConnectOrderDto = new KafkaConnectOrderDto(orderDto);
        //kafkaConnectOrderDto.setPayloadFromOrderDto(orderDto);

        //KafkaConnectOrderDtoV2 kafkaConnectOrderDto = new KafkaConnectOrderDtoV2(orderDto);

        ObjectMapper mapper = new ObjectMapper();
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        //Gson gson = new Gson();
        String jsonString = "";
        try {
            //jsonString = gson.toJson(kafkaConnectOrderDto);
            jsonString = mapper.writeValueAsString(kafkaConnectOrderDto);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        kafkaTemplate.send(topic, jsonString);
        log.info("Kafka sent data :" + orderDto);
        return kafkaConnectOrderDto;
    }
}
