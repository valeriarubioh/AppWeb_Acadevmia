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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RespuestaService {

    private final PreguntaRepository preguntaRepository;

    private final ReaccionRepository reaccionRepository;

    private final RespuestaRepository respuestaRepository;

    private final UserRepository userRepository;

    public RespuestaService(PreguntaRepository preguntaRepository,
                            ReaccionRepository reaccionRepository,
                            RespuestaRepository respuestaRepository,
                            UserRepository userRepository) {
        this.preguntaRepository = preguntaRepository;
        this.reaccionRepository = reaccionRepository;
        this.respuestaRepository = respuestaRepository;
        this.userRepository = userRepository;
    }

    public RespuestaResponse crearRespuesta(RespuestaRequest respuestaRequest, String id) {

        Optional<PreguntaEntity> preguntaOptional = preguntaRepository.findById(id);

        if (preguntaOptional.isEmpty()) {
            throw new BusinessException("La pregunta no existe");
        }
        Optional<UserEntity> user = Util.getUserAuthenticated(userRepository);
        RespuestaEntity respuestaEntity = RespuestaEntity.builder()
                .texto(respuestaRequest.getTexto())
                .codigo(respuestaRequest.getCodigo())
                .favorito(false)
                .userEntity(user.get())
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
    public void marcarFavorito(String idPregunta, String idRespuesta){
        PreguntaEntity preguntaEntity = preguntaRepository.findById(idPregunta).orElseThrow(() ->  new BusinessException("Id incorrecto"));
        RespuestaEntity respuestaEntity = respuestaRepository.findById(idRespuesta).orElseThrow(() ->  new BusinessException("Id incorrecto"));

        if (Boolean.TRUE.equals(respuestaEntity.getFavorito())) {

        }
        Optional<UserEntity> user = Util.getUserAuthenticated(userRepository);
        String userQuestion = preguntaEntity.getUserEntity().getUsername();
        String username = user.get().getUsername();

        if(Objects.equals(username, userQuestion)){
            Optional<RespuestaEntity> respuestaOptional = preguntaEntity.getRespuestaEntities().stream()
                    .filter(respuesta -> respuesta.getId().equals(idRespuesta))
                    .findFirst();
            if (respuestaOptional.isPresent()) {
                RespuestaEntity respuesta = respuestaOptional.get();
                respuesta.setFavorito(true);
                respuestaRepository.save(respuesta);
            } else {
                throw new BusinessException("La respuesta no existe en la pregunta proporcionada");
            }
        } else {
            throw new BusinessException("No tienes permisos para marcar la respuesta como favorita");
        }
    }
    public List<RespuestaResponse> obtenerRespuestas(String idPregunta) {
        List<RespuestaEntity> respuestaEntities = respuestaRepository.findAll();
        List<RespuestaResponse> respuestaResponses = new ArrayList<>();
        respuestaEntities.forEach((respuestaEntity -> {
            int likes= (int) respuestaEntity.getReacciones().stream().
                    filter(reaccion -> reaccion.getIsLike()==1).count();
            int dislikes = respuestaEntity.getReacciones().size()-likes;
            RespuestaResponse respuestaResponse = RespuestaResponse.builder()
                    .id(respuestaEntity.getId())
                    .texto(respuestaEntity.getTexto())
                    .codigo(respuestaEntity.getCodigo())
                    .reacciones(ReaccionResponse.builder().likes(likes).dislikes(dislikes).build())
                    .build();
            respuestaResponses.add(respuestaResponse);
        }));
        return respuestaResponses;
    }
}
