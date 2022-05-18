package com.hwan2272.msaecomms.dto;

import com.google.common.base.CaseFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
public class KafkaConnectOrderDtoV2 {
    private Schema schema;
    //private Payload payload;
    private Map<String, Object> payload;

    private final String sinkTopicOrdersConnectName = "sink-topic-orders-connect";

    /*public KafkaConnectOrderDtoIncrease() {
        this.setSchemaInit();
    }*/

    public KafkaConnectOrderDtoV2(OrderDto orderDto) throws Exception {
        this.setSchemaInit(orderDto);
        this.setPayloadFromOrderDto(orderDto);
    }

    public void setSchemaInit(OrderDto orderDto) {
        List<Field> fieldList = new ArrayList<>();
        for(java.lang.reflect.Field f : orderDto.getClass().getDeclaredFields()) {
            String underscoreField = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, f.getName());
            String simpleType = f.getType().getSimpleName();

            Field field = new Field(
                    (simpleType.toLowerCase().contains("string") ? "string"
                     : simpleType.toLowerCase().contains("integer") ? "int32"
                     : simpleType.toLowerCase().contains("date") ? "date" : ""),
                    true,
                    underscoreField
            );
            fieldList.add(field);
        }

        Schema sch = new Schema(
                "struct",
                fieldList,
                false,
                sinkTopicOrdersConnectName);
        this.setSchema(sch);
    }

    public void setPayloadFromOrderDto(OrderDto orderDto) throws Exception {
        Map<String, Object> payMap = new LinkedHashMap<>();
        for(java.lang.reflect.Field f : orderDto.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            String underscoreField = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, f.getName());
            Object data = f.get(orderDto);
            payMap.put(underscoreField, data);
        }
        this.setPayload(payMap);
    }

    @Data
    @AllArgsConstructor
    public class Schema {
        private String type;
        private List<Field> fields;
        private boolean optional;
        private String name;
    }

    @Data
    @AllArgsConstructor
    public class Field {
        private String type;
        private boolean optional;
        private String field;
    }

    /*@Data
    @AllArgsConstructor
    public class Payload {
        private String order_id;
        private String user_id;
        private String product_id;
        private Integer qty;
        private Integer unit_price;
        private Integer total_price;
        private Date created_at;
    }*/
}
