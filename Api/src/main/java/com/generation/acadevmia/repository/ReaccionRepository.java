package com.generation.acadevmia.repository;

import com.generation.acadevmia.entity.ReaccionEntity;
import com.generation.acadevmia.entity.UserEntity;
import com.generation.acadevmia.payload.request.EReaccionType;
import com.generation.acadevmia.payload.request.ReaccionRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReaccionRepository extends MongoRepository<ReaccionEntity, String> {
}
