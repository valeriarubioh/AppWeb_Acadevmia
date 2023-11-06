package com.generation.acadevmia.repository;

import com.generation.acadevmia.entity.ERole;
import com.generation.acadevmia.entity.RoleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<RoleEntity, String> {
  Optional<RoleEntity> findByName(ERole name);
}
