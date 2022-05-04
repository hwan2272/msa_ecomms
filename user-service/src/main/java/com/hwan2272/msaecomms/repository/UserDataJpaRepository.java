package com.hwan2272.msaecomms.repository;

import com.hwan2272.msaecomms.dto.UserDto;
import com.hwan2272.msaecomms.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataJpaRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserId(String userId);
}
