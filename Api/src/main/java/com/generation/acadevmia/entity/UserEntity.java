package com.generation.acadevmia.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@EqualsAndHashCode
@Getter
@Setter
@Builder
@Document("usuarios")
public class UserEntity {

    @Id
    private String id;
    private String username;
    private String email;
    private String name;
    private String password;
    @DBRef
    @Field(name = "roles")
    private Set<RoleEntity> roleEntities;
}
