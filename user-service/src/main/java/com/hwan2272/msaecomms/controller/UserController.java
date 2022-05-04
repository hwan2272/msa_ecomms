package com.hwan2272.msaecomms.controller;

import com.hwan2272.msaecomms.dto.UserDto;
import com.hwan2272.msaecomms.service.UserService;
import com.hwan2272.msaecomms.vo.RequestUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @GetMapping("/{userId}")
    public UserDto getUserInfo(@PathVariable(value = "userId") String userId) {
        log.info("getUserInfo");
        return userService.getUser(userId);
    }

    @PostMapping
    public void addUserInfo(@RequestBody RequestUserVO userVO) {
        log.info("addUserInfo");
        UserDto userDto = new UserDto();
        userDto.setUserId(userVO.getEmail());
        userDto.setEncPwd(userVO.getPwd());
        userService.addUser(userDto);
    }

}
