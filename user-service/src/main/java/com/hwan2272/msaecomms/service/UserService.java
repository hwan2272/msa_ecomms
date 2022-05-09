package com.hwan2272.msaecomms.service;

import com.hwan2272.msaecomms.dto.UserDto;
import com.hwan2272.msaecomms.entity.UserEntity;
import com.hwan2272.msaecomms.repository.UserDataJpaRepository;
import com.hwan2272.msaecomms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
    /*@Autowired
    UserRepository userRepository;*/

    @Autowired
    UserDataJpaRepository userDataJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userDataJpaRepository.findByEmail(username);
        if(userEntity == null) throw new UsernameNotFoundException(username);
        return new User(userEntity.getEmail(), userEntity.getEncPwd(),
                true, true, true, true,
                new ArrayList<>());
    }

    public void addUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDto.getUserId());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setName(userDto.getName());
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
