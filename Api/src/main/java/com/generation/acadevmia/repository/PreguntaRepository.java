package com.generation.acadevmia.repository;


import com.generation.acadevmia.entity.PreguntaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PreguntaRepository extends MongoRepository<PreguntaEntity, String> {
    Optional<PreguntaEntity> findByRespuestaEntities(@Param("id") String idRespuesta);
}
