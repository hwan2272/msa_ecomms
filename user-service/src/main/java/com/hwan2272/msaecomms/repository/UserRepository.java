package com.hwan2272.msaecomms.repository;

import com.hwan2272.msaecomms.dto.UserDto;
import com.hwan2272.msaecomms.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserRepository {

    @PersistenceContext
    EntityManager em;

    public UserDto save(UserDto userDto) {
        em.persist(userDto);
        return userDto;
    }

    public UserDto find(String id) {
        return em.find(UserDto.class, id);
    }
}
