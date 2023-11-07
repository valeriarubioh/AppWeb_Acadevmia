package com.generation.acadevmia.service;

import com.generation.acadevmia.entity.PreguntaEntity;
import com.generation.acadevmia.entity.ReaccionEntity;
import com.generation.acadevmia.entity.RespuestaEntity;
import com.generation.acadevmia.entity.UserEntity;
import com.generation.acadevmia.exception.BusinessException;
import com.generation.acadevmia.payload.request.EReaccionType;
import com.generation.acadevmia.payload.request.ReaccionRequest;
import com.generation.acadevmia.repository.PreguntaRepository;
import com.generation.acadevmia.repository.ReaccionRepository;
import com.generation.acadevmia.repository.RespuestaRepository;
import com.generation.acadevmia.repository.UserRepository;
import com.generation.acadevmia.utilities.Util;
import org.springframework.stereotype.Service;

import java.util.Objects;
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
        ReaccionEntity reaccionEntity = ReaccionEntity.builder()
                .isLike(reaccionRequest.getIsLike())
                .userEntity(Util.getUserAuthenticated(userRepository))
                .build();
        UserEntity userEntity = Util.getUserAuthenticated(userRepository);

        if (reaccionRequest.getTipo().compareTo(EReaccionType.PREGUNTA) == 0) {
            PreguntaEntity preguntaEntity = preguntaRepository.findById(reaccionRequest.getId())
                    .orElseThrow(() -> new BusinessException("Pregunta no encontrada"));
            if (Objects.equals(preguntaEntity.getUserEntity().getUsername(), userEntity.getUsername())) {
                throw new BusinessException("No se puede reaccionar a su misma publicación");
            }
            Optional<ReaccionEntity> savedReaccionAux = Optional.empty();
            for (ReaccionEntity existingReaccion : preguntaEntity.getReacciones()) {
                if (existingReaccion.getUserEntity().getUsername().equals(userEntity.getUsername())) {
                    if (existingReaccion.getIsLike() == reaccionRequest.getIsLike()) {
                        savedReaccionAux = Optional.of(existingReaccion);
                        break;
                    } else {
                        existingReaccion.setIsLike(reaccionRequest.getIsLike());
                        reaccionRepository.save(existingReaccion);
                        return;
                    }
                }
            }
            if (savedReaccionAux.isPresent()) {
                preguntaEntity.getReacciones().remove(savedReaccionAux.get());
                preguntaRepository.save(preguntaEntity);
                reaccionRepository.delete(savedReaccionAux.get());
                return;
            }
            ReaccionEntity savedReaccionEntity = reaccionRepository.save(reaccionEntity);
            preguntaEntity.getReacciones().add(savedReaccionEntity);
            preguntaRepository.save(preguntaEntity);
        } else {
            RespuestaEntity respuestaEntity = respuestaRepository.findById(reaccionRequest.getId())
                    .orElseThrow(() -> new BusinessException("Respuesta no encontrada"));
            if (Objects.equals(respuestaEntity.getUserEntity().getUsername(), userEntity.getUsername())) {
                throw new BusinessException("No se puede reaccionar a su misma publicación");
            }
            Optional<ReaccionEntity> savedReaccionAux = Optional.empty();
            for (ReaccionEntity existingReaccion : respuestaEntity.getReacciones()) {
                if (existingReaccion.getUserEntity().getUsername().equals(userEntity.getUsername())) {
                    if (existingReaccion.getIsLike() == reaccionRequest.getIsLike()) {
                        savedReaccionAux = Optional.of(existingReaccion);
                        break;
                    } else {
                        existingReaccion.setIsLike(reaccionRequest.getIsLike());
                        reaccionRepository.save(existingReaccion);
                        return;
                    }
                }
            }
            if (savedReaccionAux.isPresent()) {
                respuestaEntity.getReacciones().remove(savedReaccionAux.get());
                respuestaRepository.save(respuestaEntity);
                reaccionRepository.delete(savedReaccionAux.get());
                return;
            }
            ReaccionEntity savedReaccionEntity = reaccionRepository.save(reaccionEntity);
            respuestaEntity.getReacciones().add(savedReaccionEntity);
            respuestaRepository.save(respuestaEntity);
        }
    }

}
