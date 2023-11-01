package com.generation.acadevmia.repository;

import com.generation.acadevmia.model.ERole;
import com.generation.acadevmia.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
