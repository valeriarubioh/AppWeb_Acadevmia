package com.generation.acadevmia.service;

import com.generation.acadevmia.entity.UserEntity;
import com.generation.acadevmia.exception.BusinessException;
import com.generation.acadevmia.entity.PreguntaEntity;
import com.generation.acadevmia.entity.RespuestaEntity;
import com.generation.acadevmia.payload.request.RespuestaRequest;
import com.generation.acadevmia.payload.response.ReaccionResponse;
import com.generation.acadevmia.payload.response.RespuestaResponse;
import com.generation.acadevmia.payload.response.UserResponse;
import com.generation.acadevmia.repository.PreguntaRepository;
import com.generation.acadevmia.repository.ReaccionRepository;
import com.generation.acadevmia.repository.RespuestaRepository;
import com.generation.acadevmia.repository.UserRepository;
import com.generation.acadevmia.utilities.Util;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RespuestaService {

    private final PreguntaRepository preguntaRepository;

    private final RespuestaRepository respuestaRepository;

    private final UserRepository userRepository;

    public RespuestaService(PreguntaRepository preguntaRepository,
                            RespuestaRepository respuestaRepository,
                            UserRepository userRepository) {
        this.preguntaRepository = preguntaRepository;
        this.respuestaRepository = respuestaRepository;
        this.userRepository = userRepository;
    }

    public RespuestaResponse crearRespuesta(RespuestaRequest respuestaRequest, String id) {

        Optional<PreguntaEntity> preguntaOptional = preguntaRepository.findById(id);

        if (preguntaOptional.isEmpty()) {
            throw new BusinessException("La pregunta no existe");
        }
        UserEntity userEntity = Util.getUserAuthenticated(userRepository);
        RespuestaEntity respuestaEntity = RespuestaEntity.builder()
                .texto(respuestaRequest.getTexto())
                .codigo(respuestaRequest.getCodigo())
                .favorito(false)
                .userEntity(userEntity)
                .reacciones(new ArrayList<>()).build();

        RespuestaEntity savedRespuestaEntity = respuestaRepository.save(respuestaEntity);
        PreguntaEntity preguntaEntity = preguntaOptional.get();
        preguntaEntity.getRespuestaEntities().add(savedRespuestaEntity);
        preguntaRepository.save(preguntaEntity);

        return RespuestaResponse.builder()
            .id(respuestaEntity.getId())
            .texto(respuestaEntity.getTexto())
            .codigo(respuestaEntity.getCodigo())
            .favorito(respuestaEntity.getFavorito())
            .user(UserResponse
                    .builder()
                    .username(respuestaEntity.getUserEntity().getUsername())
                    .name(respuestaEntity.getUserEntity().getName())
                    .build())
            .reacciones(ReaccionResponse.builder()
                    .likes(0)
                    .dislikes(0)
                    .build())
            .build();
    }
    public RespuestaResponse marcarFavorito(String idRespuesta){
        PreguntaEntity preguntaEntity = preguntaRepository.findByRespuestaEntities(idRespuesta)
                .orElseThrow(() -> new BusinessException("No se encontro la pregunta"));
        RespuestaEntity respuestaEntity = respuestaRepository.findById(idRespuesta)
                .orElseThrow(() ->  new BusinessException("Id respuesta incorrecto"));
        UserEntity userEntity = Util.getUserAuthenticated(userRepository);

        if (Boolean.TRUE.equals(respuestaEntity.getFavorito())) {
            respuestaEntity.setFavorito(Boolean.FALSE);
            return respuestaEntityToRespuestaResponse(respuestaRepository.save(respuestaEntity));
        }

        if(!Objects.equals(userEntity.getUsername(), preguntaEntity.getUserEntity().getUsername())){
            throw new BusinessException("No tienes permisos para marcar la respuesta como favorita");
        }

        boolean respuestaFavorita = preguntaEntity.getRespuestaEntities()
                .stream()
                .anyMatch(RespuestaEntity::getFavorito);
        if (respuestaFavorita) {
            throw new BusinessException("Ya se ha marcado una respuesta como favorita");
        }

        respuestaEntity.setFavorito(Boolean.TRUE);
        return respuestaEntityToRespuestaResponse(respuestaRepository.save(respuestaEntity));
    }

    public List<RespuestaResponse> obtenerRespuestas(String idPregunta) {
        PreguntaEntity preguntaEntity = preguntaRepository.findById(idPregunta)
                .orElseThrow(() -> new BusinessException("Id pregunta no existe"));
        return preguntaEntity.getRespuestaEntities().stream().map((this::respuestaEntityToRespuestaResponse)).toList();
    }
    private RespuestaResponse respuestaEntityToRespuestaResponse(RespuestaEntity respuestaEntity) {
        int likes= (int) respuestaEntity.getReacciones().stream().
                filter(reaccion -> reaccion.getIsLike()==1).count();
        int dislikes = respuestaEntity.getReacciones().size()-likes;
        return RespuestaResponse.builder()
                .id(respuestaEntity.getId())
                .texto(respuestaEntity.getTexto())
                .codigo(respuestaEntity.getCodigo())
                .reacciones(ReaccionResponse.builder().likes(likes).dislikes(dislikes).build())
                .favorito(respuestaEntity.getFavorito())
                .user(UserResponse
                        .builder()
                        .username(respuestaEntity.getUserEntity().getUsername())
                        .name(respuestaEntity.getUserEntity().getName())
                        .build())
                .build();
    }
}
