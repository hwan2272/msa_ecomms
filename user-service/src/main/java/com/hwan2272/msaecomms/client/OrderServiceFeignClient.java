package com.hwan2272.msaecomms.client;

import com.hwan2272.msaecomms.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderServiceFeignClient {

    @GetMapping("/orders/{userId}/orders")
    List<ResponseOrder> getOrders(@PathVariable(value = "userId") String userId);

}
