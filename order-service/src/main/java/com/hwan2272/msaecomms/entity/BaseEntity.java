package com.hwan2272.msaecomms.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter //@Data
public class BaseEntity {

    //Data Jpa Entity Auditing @createdDate 등

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date updatedDate;

    //AuditorAware Bean 선언해주어야함
    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;
}
