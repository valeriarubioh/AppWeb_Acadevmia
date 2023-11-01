package com.generation.acadevmia.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@RequiredArgsConstructor
@Document("respuestas")
public class Respuesta {
    @Id
    private String id;
    private String texto;
    private String codigo;
    private Boolean favorito;
    private User user;
    private List<Reaccion> reacciones;
}
