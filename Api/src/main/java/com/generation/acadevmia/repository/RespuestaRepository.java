package com.generation.acadevmia.repository;

import com.generation.acadevmia.model.Respuesta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RespuestaRepository extends MongoRepository<Respuesta, String> {
}
