package com.hwan2272.msaecomms.controller;

import com.hwan2272.msaecomms.dto.UserDto;
import com.hwan2272.msaecomms.service.UserService;
import com.hwan2272.msaecomms.vo.RequestUser;
import com.hwan2272.msaecomms.vo.ResponseUser;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    ModelMapper mMapper;

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

    @GetMapping("/users/{userId}")
    public ResponseEntity getUserInfo(@PathVariable String userId) {
        log.info("getUserInfo");
        UserDto userDto = userService.getUserDetails(userId.trim());
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDto);
    }

    @PostMapping("/users")
    public ResponseEntity addUserInfo(@RequestBody RequestUser requestUser) {
        log.info("addUserInfo");
        UserDto userDto = mMapper.map(requestUser, UserDto.class);
        userService.addUser(userDto);

        ResponseUser responseUser = mMapper.map(userDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseUser);
    }

}
