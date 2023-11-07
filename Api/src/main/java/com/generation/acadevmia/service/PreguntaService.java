package com.generation.acadevmia.service;

import com.generation.acadevmia.entity.PreguntaEntity;
import com.generation.acadevmia.entity.UserEntity;
import com.generation.acadevmia.exception.BusinessException;
import com.generation.acadevmia.payload.request.PreguntaRequest;
import com.generation.acadevmia.payload.response.PreguntaResponse;
import com.generation.acadevmia.repository.PreguntaRepository;
import com.generation.acadevmia.repository.UserRepository;
import com.generation.acadevmia.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.generation.acadevmia.utilities.Util.preguntaEntityToPreguntaResponse;

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
        return preguntaEntities.stream().map((Util::preguntaEntityToPreguntaResponse)).toList();
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

    public void eliminarPregunta(String idPregunta) {
        PreguntaEntity preguntaEntity = preguntaRepository.findById(idPregunta)
                .orElseThrow(() -> new BusinessException("idPregunta incorrecto"));
        UserEntity userAuthenticated = Util.getUserAuthenticated(userRepository);

        if (!Objects.equals(preguntaEntity.getUserEntity().getUsername(), userAuthenticated.getUsername())) {
            throw new BusinessException("Accion no permitida");
        }
        preguntaRepository.delete(preguntaEntity);
    }
}
