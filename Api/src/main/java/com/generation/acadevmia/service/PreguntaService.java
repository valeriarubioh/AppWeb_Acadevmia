package com.generation.acadevmia.service;

import com.generation.acadevmia.model.Pregunta;
import com.generation.acadevmia.model.User;
import com.generation.acadevmia.repository.PreguntaRepository;
import com.generation.acadevmia.repository.UserRepository;
import com.generation.acadevmia.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PreguntaService {

    @Autowired
    PreguntaRepository preguntaRepository;
    @Autowired
    UserRepository usuarioRepository;

    public Pregunta crearPregunta(Pregunta pregunta) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = usuarioRepository.findByUsername(principal.getUsername());
        pregunta.setRespuestas(new ArrayList<>());
        pregunta.setReacciones(new ArrayList<>());
        pregunta.setUser(user.get());
        return preguntaRepository.save(pregunta);
    }

    public List<Pregunta> obtenerPreguntas() {
        return preguntaRepository.findAll();
    }
}
