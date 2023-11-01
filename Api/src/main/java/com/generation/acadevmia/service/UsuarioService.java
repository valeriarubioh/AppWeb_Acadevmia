package com.generation.acadevmia.service;

import com.generation.acadevmia.model.User;
import com.generation.acadevmia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    UserRepository usuarioRepository;

    public User crearUsuario(User user) {
        return usuarioRepository.save(user);
    }
}
