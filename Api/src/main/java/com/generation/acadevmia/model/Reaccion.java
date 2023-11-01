package com.generation.acadevmia.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode
@Getter
@Setter
@RequiredArgsConstructor
@Document("reacciones")
public class Reaccion {
    @Id
    private String id;
    private int isLike;
    private User user;
}
