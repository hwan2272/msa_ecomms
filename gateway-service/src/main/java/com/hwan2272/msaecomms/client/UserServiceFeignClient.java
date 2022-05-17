package com.hwan2272.msaecomms.client;

import com.hwan2272.msaecomms.vo.ResponseUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserServiceFeignClient {

    @GetMapping("/users/{userId}")
    ResponseUser getUserInfo(@PathVariable(value = "userId") String userId);

}
