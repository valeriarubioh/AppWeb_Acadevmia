package com.generation.acadevmia.service;

import com.generation.acadevmia.entity.PreguntaEntity;
import com.generation.acadevmia.entity.ReaccionEntity;
import com.generation.acadevmia.entity.RespuestaEntity;
import com.generation.acadevmia.entity.UserEntity;
import com.generation.acadevmia.exception.BusinessException;
import com.generation.acadevmia.payload.request.RespuestaRequest;
import com.generation.acadevmia.payload.response.*;
import com.generation.acadevmia.repository.PreguntaRepository;
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
    @Autowired
    private PreguntaRepository preguntaRepository;
    @Autowired
    private ReaccionRepository reaccionRepository;
    @Autowired
    private RespuestaRepository respuestaRepository; //duda
    @Autowired
    UserRepository usuarioRepository;

    public RespuestaResponse crearRespuesta(RespuestaRequest respuestaRequest, String id) {

        Optional<Pregunta> preguntaOptional = preguntaRepository.findById(id);

        if (preguntaOptional.isEmpty()) {
            throw new RuntimeException("La pregunta no existe");
        }
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = usuarioRepository.findByUsername(principal.getUsername());
        Respuesta respuesta = Respuesta.builder()
                .texto(respuestaRequest.getTexto())
                .codigo(respuestaRequest.getCodigo())
                .favorito(false)
                .user(user.get())
                .reacciones(new ArrayList<>()).build();

        Respuesta savedRespuesta = respuestaRepository.save(respuesta);
        Pregunta pregunta = preguntaOptional.get();
        pregunta.getRespuestas().add(savedRespuesta);
        preguntaRepository.save(pregunta);

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

    public RespuestaResponse marcarFavorito(String idRespuesta) {
        PreguntaEntity preguntaEntity = preguntaRepository.findByRespuestaEntities(idRespuesta)
                .orElseThrow(() -> new BusinessException("No se encontro la pregunta"));
        RespuestaEntity respuestaEntity = respuestaRepository.findById(idRespuesta)
                .orElseThrow(() -> new BusinessException("Id respuesta incorrecto"));
        UserEntity userEntity = Util.getUserAuthenticated(userRepository);

        if (Boolean.TRUE.equals(respuestaEntity.getFavorito())) {
            respuestaEntity.setFavorito(Boolean.FALSE);
            return respuestaEntityToRespuestaResponse(respuestaRepository.save(respuestaEntity));
        }

        if (!Objects.equals(userEntity.getUsername(), preguntaEntity.getUserEntity().getUsername())) {
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

    public GetRespuestaResponse obtenerRespuestas(String idPregunta) {
        PreguntaEntity preguntaEntity = preguntaRepository.findById(idPregunta)
                .orElseThrow(() -> new BusinessException("Id pregunta no existe"));
        PreguntaResponse preguntaResponse = Util.preguntaEntityToPreguntaResponse(preguntaEntity);
        List<RespuestaResponse> respuestaResponses = preguntaEntity.getRespuestaEntities().stream().map((this::respuestaEntityToRespuestaResponse)).toList();
        return GetRespuestaResponse.builder().pregunta(preguntaResponse).respuestas(respuestaResponses).build();
    }

    private RespuestaResponse respuestaEntityToRespuestaResponse(RespuestaEntity respuestaEntity) {
        int likes = (int) respuestaEntity.getReacciones().stream().
                filter(reaccion -> reaccion.getIsLike() == 1).count();
        int dislikes = respuestaEntity.getReacciones().size() - likes;
        Optional<String> optionalUsername = Util.getUsername();
        EUserReaction reacted = EUserReaction.NONE;
        if(optionalUsername.isPresent()) {
            Optional<ReaccionEntity> userReaction = respuestaEntity.getReacciones()
                    .stream()
                    .filter(reaccionUser -> Objects.equals(reaccionUser.getUserEntity().getUsername(), optionalUsername.get()))
                    .findFirst();
            if (userReaction.isPresent()) {
                reacted = userReaction.get().getIsLike() == 1 ? EUserReaction.LIKE : EUserReaction.DISLIKE;
            }
        }
        return RespuestaResponse.builder()
                .id(respuestaEntity.getId())
                .texto(respuestaEntity.getTexto())
                .codigo(respuestaEntity.getCodigo())
                .reacciones(ReaccionResponse.builder().likes(likes).dislikes(dislikes).userHasReacted(reacted).build())
                .favorito(respuestaEntity.getFavorito())
                .user(UserResponse
                        .builder()
                        .username(respuestaEntity.getUserEntity().getUsername())
                        .name(respuestaEntity.getUserEntity().getName())
                        .build())
                .build();
    }
}
