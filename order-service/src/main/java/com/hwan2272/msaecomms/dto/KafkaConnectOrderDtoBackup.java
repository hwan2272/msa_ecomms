package com.hwan2272.msaecomms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
public class KafkaConnectOrderDtoBackup {
    private Schema schema;
    private Payload payload;

    private final String sinkTopicOrdersConnectName = "sink-topic-orders-connect";

    public KafkaConnectOrderDtoBackup() {
        this.setSchemaInit();
    }

    public KafkaConnectOrderDtoBackup(OrderDto orderDto) {
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
       /* sch.setType("struct");
        sch.setFields(
                Arrays.asList(
                        new Field("string", true, "order_id"),
                        new Field("string", true, "user_id"),
                        new Field("string", true, "product_id"),
                        new Field("int32", true, "qty"),
                        new Field("int32", true, "unit_price"),
                        new Field("int32", true, "total_price")
                )
        );
        sch.setOptional(false);
        sch.setName(sinkTopicOrdersConnectName);*/

        /*Schema.builder()
                .type("struct")
                .fields(Arrays.asList(
                                Field.builder().type("string").optional(true).field("order_id").build(),
                                Field.builder().type("string").optional(true).field("user_id").build(),
                                Field.builder().type("string").optional(true).field("product_id").build(),
                                Field.builder().type("int32").optional(true).field("qty").build(),
                                Field.builder().type("int32").optional(true).field("unit_price").build(),
                                Field.builder().type("int32").optional(true).field("total_price").build()
                        )
                ).optional(false).name("orders")
                .build();*/
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
        /*pay.setOrder_id(orderDto.getOrderId());
        pay.setUser_id(orderDto.getUserId());
        pay.setProduct_id(orderDto.getProductId());
        pay.setQty(orderDto.getQty());
        pay.setUnit_price(orderDto.getUnitPrice());
        pay.setTotal_price(orderDto.getTotalPrice());
        pay.setCreated_at(orderDto.getCreatedAt());*/
        /*Payload.builder()
                .order_id(orderDto.getOrderId())
                .user_id(orderDto.getUserId())
                .product_id(orderDto.getProductId())
                .qty(orderDto.getQty())
                .unit_price(orderDto.getUnitPrice())
                .total_price(orderDto.getTotalPrice())
                .created_at(orderDto.getCreatedAt())
                .build();*/
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
