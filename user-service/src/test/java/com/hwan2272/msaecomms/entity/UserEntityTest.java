package com.hwan2272.msaecomms.entity;

import com.hwan2272.msaecomms.repository.UserDataJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
class UserEntityTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    UserDataJpaRepository dataJpaRepository;

    @Test
    @Order(1)
    public void testEntity() {
        UserEntity user = new UserEntity();
        user.setUserId("emailId");
        user.setEncPwd("encPwd");
        user.setCreatedAt(new Date());

        em.persist(user);
        System.out.println("::::jpa Repo :: user ::: " + user.toString());

    }

    @Test
    @Order(2)
    public void testEntity2() {
        UserEntity user = new UserEntity();
        user.setUserId("emailId2");
        user.setEncPwd("encPwd2");
        user.setCreatedAt(new Date());

        dataJpaRepository.save(user);
        System.out.println("::::dataJpa Repo :: user ::: " + user.toString());

    }

    @Test
    @Order(3)
    public void testEntity3() {
        String userId = "emailId2";
        System.out.println("::::dataJpa Repo :: getuser ::: " + dataJpaRepository.findByUserId(userId));

    }

}