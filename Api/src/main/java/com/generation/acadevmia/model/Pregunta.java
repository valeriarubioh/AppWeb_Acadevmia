package com.generation.acadevmia.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@Builder
@Document("preguntas")
public class Pregunta {

    @Id
    private String id;
    private String titulo;
    private String descripcion;
    private String tag;
    private Usuario usuario;
//    private List<Respuesta> respuestas;
//    private List<Reaccion> reacciones;
}
