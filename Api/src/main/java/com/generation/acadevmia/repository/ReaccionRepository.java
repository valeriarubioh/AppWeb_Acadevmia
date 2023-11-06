package com.generation.acadevmia.repository;

import com.generation.acadevmia.entity.ReaccionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReaccionRepository extends MongoRepository<ReaccionEntity, String> {
}
