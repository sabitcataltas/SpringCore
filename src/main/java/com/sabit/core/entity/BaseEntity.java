package com.sabit.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.sabit.core.utils.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Version
    private Long version;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = Status.ACTIVE; // Set default value if name is not provided
        }
        if (createdAt == null) {
            createdAt = new Date();
        }
        if (createdBy == null) {

        }
    }

    @PreUpdate
    public void preUpdate() {
        if (status == null) {
            status = Status.ACTIVE; // Set default value if name is not provided
        }
        updatedAt = new Date();

    }

}
