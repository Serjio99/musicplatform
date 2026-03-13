package com.musicplatform.user.entity;

import com.musicplatform.common.entity.SoftDeletableEntity;
import com.musicplatform.common.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity extends SoftDeletableEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.USER;

    @Column
    private String phone;

    @Column
    private String organizationName;

    @Column
    private String inn;

    @Column
    private String ogrn;

}