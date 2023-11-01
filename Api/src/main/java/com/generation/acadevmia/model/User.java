package com.generation.acadevmia.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@EqualsAndHashCode
@Getter
@Setter
@Builder
@Document("usuarios")
public class User {

    @Id
    private String id;
    private String username;
    private String email;
    private String name;
    private String password;
    @DBRef
    private Set<Role> roles;
}
