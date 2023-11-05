package com.generation.acadevmia.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode
@Getter
@Setter
@RequiredArgsConstructor
@Document("reacciones")
public class ReaccionEntity {
    @Id
    private String id;
    private int isLike;
    @Field(name = "user")
    private UserEntity userEntity;

    @Override
    public String toString() {
        return "Reaccion{" +
                "id='" + id + '\'' +
                ", isLike=" + isLike +
                ", user=" + userEntity +
                '}';
    }
}
