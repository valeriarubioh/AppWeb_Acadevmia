package com.generation.acadevmia.service;

import com.generation.acadevmia.entity.PreguntaEntity;
import com.generation.acadevmia.entity.ReaccionEntity;
import com.generation.acadevmia.entity.UserEntity;
import com.generation.acadevmia.payload.response.PreguntaResponse;
import com.generation.acadevmia.payload.response.ReaccionResponse;
import com.generation.acadevmia.payload.response.UserResponse;
import com.generation.acadevmia.repository.PreguntaRepository;
import com.generation.acadevmia.repository.UserRepository;
import com.generation.acadevmia.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PreguntaService {

    @Autowired
    PreguntaRepository preguntaRepository;
    @Autowired
    UserRepository userRepository;

    public PreguntaResponse crearPregunta(PreguntaEntity preguntaEntity) {
        Optional<UserEntity> user = Util.getUserAuthenticated(userRepository);
        preguntaEntity.setRespuestaEntities(new ArrayList<>());
        preguntaEntity.setReacciones(new ArrayList<>());
        preguntaEntity.setUserEntity(user.get());
        PreguntaEntity preguntaEntitySaved = preguntaRepository.save(preguntaEntity);
        return PreguntaResponse.builder()
            .id(preguntaEntitySaved.getId())
            .titulo(preguntaEntitySaved.getTitulo())
            .descripcion(preguntaEntitySaved.getDescripcion())
            .tag(preguntaEntitySaved.getTag())
            .user(UserResponse
                    .builder()
                    .username(preguntaEntitySaved.getUserEntity().getUsername())
                    .name(preguntaEntitySaved.getUserEntity().getName())
                    .build())
            .reacciones(ReaccionResponse.builder().likes(0).dislikes(0).build())
            .build();
    }

    public List<PreguntaResponse> obtenerPreguntas() {
        List<PreguntaEntity> preguntaEntities = preguntaRepository.findAll();
        List<PreguntaResponse> preguntaResponses = new ArrayList<>();

        preguntaEntities.forEach((pregunta -> {
            int preguntaLikes = 0;
            int preguntaDislikes = 0;
            for (ReaccionEntity reaccionEntity :pregunta.getReacciones()){
                if (reaccionEntity.getIsLike()==1){
                    preguntaLikes++;
                }else {
                    preguntaDislikes++;
                }
            }
//            ArrayList<RespuestaResponse> respuestaResponses = new ArrayList<>();
//            for (Respuesta respuesta: pregunta.getRespuestas()) {
//                int respuestaLikes = 0, respuestaDislikes = 0;
//                for (Reaccion reaccion: respuesta.getReacciones()){
//                    if (reaccion.getIsLike()==1){
//                        respuestaLikes++;
//                    }else {
//                        respuestaDislikes++;
//                    }
//                }
//                RespuestaResponse respuestaResponse = RespuestaResponse.builder()
//                        .id(respuesta.getId())
//                        .texto(respuesta.getTexto())
//                        .codigo(respuesta.getCodigo())
//                        .favorito(respuesta.getFavorito())
//                        .user(UserResponse
//                                .builder()
//                                .username(respuesta.getUser().getUsername())
//                                .name(respuesta.getUser().getName())
//                                .build())
//                        .reacciones(ReaccionResponse.builder()
//                                .likes(respuestaLikes)
//                                .dislikes(respuestaDislikes)
//                                .build())
//                        .build();
//                respuestaResponses.add(respuestaResponse);
//            }

            PreguntaResponse preguntaResponse = PreguntaResponse.builder()
                    .id(pregunta.getId())
                    .titulo(pregunta.getTitulo())
                    .descripcion(pregunta.getDescripcion())
                    .tag(pregunta.getTag())
                    .user(UserResponse
                            .builder()
                            .username(pregunta.getUserEntity().getUsername())
                            .name(pregunta.getUserEntity().getName())
                            .build())
                            .reacciones(ReaccionResponse.builder().likes(preguntaLikes).dislikes(preguntaDislikes).build())
                    .build();
            preguntaResponses.add(preguntaResponse);

        }));

        return preguntaResponses;
    }
}
