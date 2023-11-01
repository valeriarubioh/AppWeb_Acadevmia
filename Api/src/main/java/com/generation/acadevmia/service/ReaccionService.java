package com.generation.acadevmia.service;

import com.generation.acadevmia.model.Pregunta;
import com.generation.acadevmia.model.Reaccion;
import com.generation.acadevmia.model.Respuesta;
import com.generation.acadevmia.model.User;
import com.generation.acadevmia.repository.PreguntaRepository;
import com.generation.acadevmia.repository.ReaccionRepository;
import com.generation.acadevmia.repository.RespuestaRepository;
import com.generation.acadevmia.repository.UserRepository;
import com.generation.acadevmia.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReaccionService {

    @Autowired
    private PreguntaRepository preguntaRepository;
    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private ReaccionRepository reaccionRepository;

    @Autowired
    UserRepository usuarioRepository;
    public Pregunta crearReaccion(Reaccion reaccion, String id) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = usuarioRepository.findByUsername(principal.getUsername());
        reaccion.setUser(user.get());
        Optional<Pregunta> preguntaOptional = preguntaRepository.findById(id);
        if (preguntaOptional.isEmpty()) {
            Optional<Respuesta> respuesta = respuestaRepository.findById(id);
        } else {
            Reaccion savedReaccion = reaccionRepository.save(reaccion);
            Pregunta pregunta = preguntaOptional.get();
            pregunta.getReacciones().add(savedReaccion);
            Pregunta preguntaSaved = preguntaRepository.save(pregunta);
            return preguntaSaved;
        }
        return null;
    }
}
