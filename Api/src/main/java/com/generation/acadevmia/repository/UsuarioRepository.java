package com.generation.acadevmia.repository;

import com.generation.acadevmia.model.GroceryItem;
import com.generation.acadevmia.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByUsername(String username);
}
