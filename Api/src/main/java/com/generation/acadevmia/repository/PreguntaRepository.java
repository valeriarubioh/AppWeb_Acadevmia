package com.generation.acadevmia.repository;


import com.generation.acadevmia.model.Pregunta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PreguntaRepository extends MongoRepository<Pregunta, String> {
}
