package com.generation.acadevmia.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

@EqualsAndHashCode
@Getter
@Setter
@Builder
@Document("respuestas")
public class RespuestaEntity {
    @Id
    private String id;
    private String texto;
    private String codigo;
    private Boolean favorito;
    @Field(name = "user")
    private UserEntity userEntity;
    @DBRef
    @Field(name = "reacciones")
    private ArrayList<ReaccionEntity> reacciones;
}
