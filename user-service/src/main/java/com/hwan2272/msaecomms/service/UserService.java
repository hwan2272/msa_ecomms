package com.hwan2272.msaecomms.service;

import com.hwan2272.msaecomms.dto.UserDto;
import com.hwan2272.msaecomms.entity.UserEntity;
import com.hwan2272.msaecomms.repository.UserDataJpaRepository;
import com.hwan2272.msaecomms.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    /*@Autowired
    UserRepository userRepository;*/

    @Autowired
    ModelMapper mMapper;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserDataJpaRepository userDataJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userDataJpaRepository.findByEmail(email);
        if(userEntity == null) throw new UsernameNotFoundException(email);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(userEntity.getEmail(), userEntity.getEncPwd(),
                true, true, true, true,
                authorities);
    }

    public void addUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setEncPwd(passwordEncoder.encode(userDto.getPwd()));
        UserEntity userEntity = mMapper.map(userDto, UserEntity.class);
        userDataJpaRepository.save(userEntity);
    }

    public UserDto getUserDetails(String userId) {
        UserEntity userEntity = userDataJpaRepository.findByUserId(userId);
        UserDto userDto = mMapper.map(userEntity, UserDto.class);
        return userDto;
    }

    public UserDto getUser(String userEmail) {
        UserEntity userEntity = userDataJpaRepository.findByEmail(userEmail);
        UserDto userDto = mMapper.map(userEntity, UserDto.class);
        return userDto;
    }

}
