package com.generation.acadevmia.repository;


import com.generation.acadevmia.entity.PreguntaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PreguntaRepository extends MongoRepository<PreguntaEntity, String> {
}
