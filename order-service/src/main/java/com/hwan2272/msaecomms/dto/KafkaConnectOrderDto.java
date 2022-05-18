package com.hwan2272.msaecomms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
public class KafkaConnectOrderDto {
    private Schema schema;
    private Payload payload;

    private final String sinkTopicOrdersConnectName = "sink-topic-orders-connect";

    public KafkaConnectOrderDto() {
        this.setSchemaInit();
    }

    public KafkaConnectOrderDto(OrderDto orderDto) {
        this.setSchemaInit();
        this.setPayloadFromOrderDto(orderDto);
    }

    public void setSchemaInit() {
        Schema sch = new Schema("struct",
                Arrays.asList(
                    new Field("string", true, "order_id"),
                    new Field("string", true, "user_id"),
                    new Field("string", true, "product_id"),
                    new Field("int32", true, "qty"),
                    new Field("int32", true, "unit_price"),
                    new Field("int32", true, "total_price")
                ),
                false,
                sinkTopicOrdersConnectName);
        this.setSchema(sch);
    }

    public void setPayloadFromOrderDto(OrderDto orderDto) {
        Payload pay = new Payload(
                orderDto.getOrderId(),
                orderDto.getUserId(),
                orderDto.getProductId(),
                orderDto.getQty(),
                orderDto.getUnitPrice(),
                orderDto.getTotalPrice(),
                orderDto.getCreatedAt()
        );
        this.setPayload(pay);
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

    @Data
    @AllArgsConstructor
    public class Payload {
        private String order_id;
        private String user_id;
        private String product_id;
        private Integer qty;
        private Integer unit_price;
        private Integer total_price;
        private Date created_at;
    }
}
