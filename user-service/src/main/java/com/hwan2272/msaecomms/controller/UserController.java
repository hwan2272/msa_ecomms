package com.hwan2272.msaecomms.controller;

import com.hwan2272.msaecomms.dto.UserDto;
import com.hwan2272.msaecomms.service.UserService;
import com.hwan2272.msaecomms.vo.RequestUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

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
        return String.format(health, "UserService");
    }

    @GetMapping("/{userId}")
    public UserDto getUserInfo(@PathVariable(value = "userId") String userId) {
        log.info("getUserInfo");
        return userService.getUser(userId);
    }

    @PostMapping
    public void addUserInfo(@RequestBody RequestUser requestUser) {
        log.info("addUserInfo");
        UserDto userDto = new UserDto();
        userDto.setUserId(requestUser.getEmail());
        userDto.setEncPwd(requestUser.getPwd());
        userService.addUser(userDto);
    }

}
