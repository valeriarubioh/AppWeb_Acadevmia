package com.generation.acadevmia.service;

import com.generation.acadevmia.model.Pregunta;
import com.generation.acadevmia.model.Usuario;
import com.generation.acadevmia.repository.PreguntaRepository;
import com.generation.acadevmia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreguntaService {

    @Autowired
    PreguntaRepository preguntaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    public Pregunta crearPregunta(Pregunta pregunta) {
        Optional<Usuario> usuario = usuarioRepository.findByUsername(pregunta.getUsuario().getUsername());
        pregunta.setUsuario(usuario.get());
        return preguntaRepository.save(pregunta);
    }

    public List<Pregunta> obtenerPreguntas() {
        return preguntaRepository.findAll();
    }
}
