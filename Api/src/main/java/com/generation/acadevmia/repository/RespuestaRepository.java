package com.generation.acadevmia.repository;

import com.generation.acadevmia.entity.RespuestaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RespuestaRepository extends MongoRepository<RespuestaEntity, String> {
}
