package com.hwan2272.msaecomms.service;

import com.hwan2272.msaecomms.dto.UserDto;
import com.hwan2272.msaecomms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void addUser(UserDto userDto) {
        userRepository.save(userDto);
    }

    public UserDto getUser(String userId) {
        return userRepository.find(userId);
    }
}
