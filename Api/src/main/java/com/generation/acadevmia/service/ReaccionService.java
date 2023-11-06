package com.generation.acadevmia.service;

import com.generation.acadevmia.entity.PreguntaEntity;
import com.generation.acadevmia.entity.ReaccionEntity;
import com.generation.acadevmia.entity.RespuestaEntity;
import com.generation.acadevmia.entity.UserEntity;
import com.generation.acadevmia.exception.BusinessException;
import com.generation.acadevmia.payload.request.EReaccionType;
import com.generation.acadevmia.payload.request.ReaccionRequest;
import com.generation.acadevmia.payload.response.ReaccionResponse;
import com.generation.acadevmia.payload.response.RespuestaResponse;
import com.generation.acadevmia.payload.response.UserResponse;
import com.generation.acadevmia.repository.PreguntaRepository;
import com.generation.acadevmia.repository.ReaccionRepository;
import com.generation.acadevmia.repository.RespuestaRepository;
import com.generation.acadevmia.repository.UserRepository;
import com.generation.acadevmia.security.services.UserDetailsImpl;
import com.generation.acadevmia.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void crearReaccion(ReaccionRequest reaccionRequest) {

        //TODO Verificar que el usuario no tenga reaccciones y en caso que tenga cambiar de estado (like o dislike)
        ReaccionEntity reaccionEntity = ReaccionEntity.builder()
            .isLike(reaccionRequest.getIsLike())
            .userEntity(Util.getUserAuthenticated(userRepository))
            .build();
        UserEntity userEntity = Util.getUserAuthenticated(userRepository);

        if (reaccionRequest.getTipo().compareTo(EReaccionType.PREGUNTA) == 0){
            PreguntaEntity preguntaEntity = preguntaRepository.findById(reaccionRequest.getId())
                    .orElseThrow(() -> new BusinessException("Pregunta no encontrada"));
            for (ReaccionEntity existingReaccion : preguntaEntity.getReacciones()) {
                if(existingReaccion.getUserEntity().getUsername().equals(userEntity.getUsername())) {
                    if (existingReaccion.getIsLike() != reaccionRequest.getIsLike()) {
                        existingReaccion.setIsLike(reaccionRequest.getIsLike());
                        reaccionRepository.save(existingReaccion);
                        return;
                    } throw new BusinessException("La reacción ya ha sido marcada");
                }
            }
            ReaccionEntity savedReaccionEntity = reaccionRepository.save(reaccionEntity);
            preguntaEntity.getReacciones().add(savedReaccionEntity);
            preguntaRepository.save(preguntaEntity);
        } else {
            RespuestaEntity respuestaEntity = respuestaRepository.findById(reaccionRequest.getId())
                    .orElseThrow(() -> new BusinessException("Respuesta no encontrada"));
            ReaccionEntity savedReaccionEntity = reaccionRepository.save(reaccionEntity);
            respuestaEntity.getReacciones().add(savedReaccionEntity);
            respuestaRepository.save(respuestaEntity);
        }
    }

}
