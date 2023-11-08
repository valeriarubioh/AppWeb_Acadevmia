package com.generation.acadevmia.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.generation.acadevmia.entity.UserEntity;
import com.generation.acadevmia.model.ProfilePicture;
import com.generation.acadevmia.model.User;

public interface ProfilePictureRepository extends MongoRepository<ProfilePicture, String> {

	Optional<ProfilePicture> findByUser(UserEntity user);
}
