package com.generation.acadevmia.service;

import com.generation.acadevmia.entity.PreguntaEntity;
import com.generation.acadevmia.entity.ReaccionEntity;
import com.generation.acadevmia.entity.RespuestaEntity;
import com.generation.acadevmia.entity.UserEntity;
import com.generation.acadevmia.exception.BusinessException;
import com.generation.acadevmia.repository.PreguntaRepository;
import com.generation.acadevmia.repository.ReaccionRepository;
import com.generation.acadevmia.repository.RespuestaRepository;
import com.generation.acadevmia.repository.UserRepository;
import com.generation.acadevmia.security.services.UserDetailsImpl;
import com.generation.acadevmia.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReaccionService {

    private final PreguntaRepository preguntaRepository;
    private final RespuestaRepository respuestaRepository;
    private final ReaccionRepository reaccionRepository;
    private final UserRepository userRepository;

    public ReaccionService(PreguntaRepository preguntaRepository,
                           RespuestaRepository respuestaRepository,
                           ReaccionRepository reaccionRepository,
                           UserRepository userRepository) {
        this.preguntaRepository = preguntaRepository;
        this.respuestaRepository = respuestaRepository;
        this.reaccionRepository = reaccionRepository;
        this.userRepository = userRepository;
    }

    public void crearReaccion(ReaccionEntity reaccionEntity, String id) {
        UserEntity user =  Util.getUserAuthenticated(userRepository).orElseThrow(() -> new BusinessException("User no autenticado"));
        reaccionEntity.setUserEntity(user);
        Optional<PreguntaEntity> preguntaOptional = preguntaRepository.findById(id);
        if (preguntaOptional.isPresent()) {
            ReaccionEntity savedReaccionEntity = reaccionRepository.save(reaccionEntity);
            PreguntaEntity preguntaEntity = preguntaOptional.get();
            preguntaEntity.getReacciones().add(savedReaccionEntity);
            preguntaRepository.save(preguntaEntity);
        }
        RespuestaEntity respuestaEntity = respuestaRepository.findById(id).orElseThrow(() -> new BusinessException("Id incorrecto"));
        ReaccionEntity savedReaccionEntity = reaccionRepository.save(reaccionEntity);
        respuestaEntity.getReacciones().add(savedReaccionEntity);
        respuestaRepository.save(respuestaEntity);
    }
}
