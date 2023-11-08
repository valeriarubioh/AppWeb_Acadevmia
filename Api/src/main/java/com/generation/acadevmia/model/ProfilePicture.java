package com.generation.acadevmia.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.generation.acadevmia.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Document("images")
@AllArgsConstructor
public class ProfilePicture {

    @Id
    private String id;
    private String img;
    @DBRef
    private UserEntity user;
}
