package com.generation.acadevmia.repository;

import com.generation.acadevmia.model.Reaccion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReaccionRepository extends MongoRepository<Reaccion, String> {
}
