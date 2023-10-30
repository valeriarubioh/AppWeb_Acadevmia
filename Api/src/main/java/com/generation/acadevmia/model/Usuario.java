package com.generation.acadevmia.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode
@Getter
@Setter
@Builder
@Document("usuarios")
public class Usuario {

    @Id
    private String id;
    private String username;
    private String email;
    private String name;
    private String password;
}
