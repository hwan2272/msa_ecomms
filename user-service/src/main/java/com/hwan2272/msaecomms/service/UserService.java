package com.hwan2272.msaecomms.service;

import com.hwan2272.msaecomms.dto.UserDto;
import com.hwan2272.msaecomms.entity.UserEntity;
import com.hwan2272.msaecomms.repository.UserDataJpaRepository;
import com.hwan2272.msaecomms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    /*@Autowired
    UserRepository userRepository;*/

    @Autowired
    UserDataJpaRepository userDataJpaRepository;

    public void addUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDto.getUserId());
        userEntity.setEncPwd(userDto.getEncPwd());
        userDataJpaRepository.save(userEntity);
    }

    public UserDto getUser(String userId) {
        UserEntity userEntity = userDataJpaRepository.findByUserId(userId);
        UserDto userDto = new UserDto();
        userDto.setUserId(userEntity.getUserId());
        return userDto;
    }
}
