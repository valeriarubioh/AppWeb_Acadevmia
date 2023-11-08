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
@Document("preguntas")
public class PreguntaEntity {

    @Id
    private String id;
    private String titulo;
    private String descripcion;
    private String tag;
    @Field(name = "user")
    private UserEntity userEntity;
    @DBRef
    @Field(name = "respuestas")
    private ArrayList<RespuestaEntity> respuestaEntities;
    @DBRef
    @Field(name = "reacciones")
    private ArrayList<ReaccionEntity> reacciones;
}
