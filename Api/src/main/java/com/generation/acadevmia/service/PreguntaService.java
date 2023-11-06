package com.generation.acadevmia.service;

import com.generation.acadevmia.entity.PreguntaEntity;
import com.generation.acadevmia.entity.ReaccionEntity;
import com.generation.acadevmia.entity.UserEntity;
import com.generation.acadevmia.payload.request.PreguntaRequest;
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

    public PreguntaResponse crearPregunta(PreguntaRequest preguntaRequest) {
        PreguntaEntity preguntaEntitySaved = preguntaRepository.save(preguntaRequestToPreguntaEntity(preguntaRequest));
        return preguntaEntityToPreguntaResponse(preguntaEntitySaved);
    }

    public List<PreguntaResponse> obtenerPreguntas() {
        List<PreguntaEntity> preguntaEntities = preguntaRepository.findAll();
        return preguntaEntities.stream().map((this::preguntaEntityToPreguntaResponse)).toList();
    }

    private PreguntaEntity preguntaRequestToPreguntaEntity(PreguntaRequest preguntaRequest) {
        return PreguntaEntity
                .builder()
                .titulo(preguntaRequest.getTitulo())
                .tag(preguntaRequest.getTag())
                .descripcion(preguntaRequest.getDescripcion())
                .respuestaEntities(new ArrayList<>())
                .reacciones(new ArrayList<>())
                .userEntity(Util.getUserAuthenticated(userRepository))
                .build();
    }

    private PreguntaResponse preguntaEntityToPreguntaResponse(PreguntaEntity preguntaEntity) {
        int likes = (int) preguntaEntity.getReacciones().stream().
                filter(reaccion -> reaccion.getIsLike()==1).count();
        int dislikes = preguntaEntity.getReacciones().size()-likes;
        return PreguntaResponse.builder()
                .id(preguntaEntity.getId())
                .titulo(preguntaEntity.getTitulo())
                .descripcion(preguntaEntity.getDescripcion())
                .tag(preguntaEntity.getTag())
                .user(UserResponse
                        .builder()
                        .username(preguntaEntity.getUserEntity().getUsername())
                        .name(preguntaEntity.getUserEntity().getName())
                        .build())
                .reacciones(ReaccionResponse.builder().likes(likes).dislikes(dislikes).build())
                .build();
    }
}
