package com.generation.acadevmia.service;

import com.generation.acadevmia.entity.PreguntaEntity;
import com.generation.acadevmia.entity.ReaccionEntity;
import com.generation.acadevmia.entity.RespuestaEntity;
import com.generation.acadevmia.entity.UserEntity;
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
    public PreguntaEntity crearReaccion(ReaccionEntity reaccionEntity, String id) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserEntity> user = usuarioRepository.findByUsername(principal.getUsername());
        reaccionEntity.setUserEntity(user.get());
        Optional<PreguntaEntity> preguntaOptional = preguntaRepository.findById(id);
        if (preguntaOptional.isEmpty()) {
            Optional<RespuestaEntity> respuesta = respuestaRepository.findById(id);
        } else {
            ReaccionEntity savedReaccionEntity = reaccionRepository.save(reaccionEntity);
            PreguntaEntity preguntaEntity = preguntaOptional.get();
            preguntaEntity.getReacciones().add(savedReaccionEntity);
            PreguntaEntity preguntaEntitySaved = preguntaRepository.save(preguntaEntity);
            return preguntaEntitySaved;
        }
        return null;
    }
}
