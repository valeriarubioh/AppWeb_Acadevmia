package com.generation.acadevmia.service;

<<<<<<< HEAD
import com.generation.acadevmia.model.Pregunta;
import com.generation.acadevmia.model.Reaccion;
import com.generation.acadevmia.model.User;
=======
import com.generation.acadevmia.entity.PreguntaEntity;
import com.generation.acadevmia.entity.UserEntity;
import com.generation.acadevmia.exception.BusinessException;
import com.generation.acadevmia.payload.request.PreguntaRequest;
>>>>>>> 60b10761f6cb34082b331ead0860f011713b4e69
import com.generation.acadevmia.payload.response.PreguntaResponse;
import com.generation.acadevmia.repository.PreguntaRepository;
import com.generation.acadevmia.repository.UserRepository;
import com.generation.acadevmia.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    UserRepository usuarioRepository;

    public PreguntaResponse crearPregunta(Pregunta pregunta) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = usuarioRepository.findByUsername(principal.getUsername());
        pregunta.setRespuestas(new ArrayList<>());
        pregunta.setReacciones(new ArrayList<>());
        pregunta.setUser(user.get());
        Pregunta preguntaSaved = preguntaRepository.save(pregunta);
        PreguntaResponse preguntaResponse = PreguntaResponse.builder()
            .id(preguntaSaved.getId())
            .titulo(preguntaSaved.getTitulo())
            .descripcion(preguntaSaved.getDescripcion())
            .tag(preguntaSaved.getTag())
            .user(UserResponse
                    .builder()
                    .username(preguntaSaved.getUser().getUsername())
                    .name(preguntaSaved.getUser().getName())
                    .build())
            .reacciones(ReaccionResponse.builder().likes(0).dislikes(0).build())
            .build();
        return preguntaResponse;
    }

    public List<PreguntaResponse> obtenerPreguntas() {
<<<<<<< HEAD
        List<Pregunta> preguntas = preguntaRepository.findAll();
        List<PreguntaResponse> preguntaResponses = new ArrayList<>();

        preguntas.forEach((pregunta -> {
            int preguntaLikes = 0;
            int preguntaDislikes = 0;
            for (Reaccion reaccion:pregunta.getReacciones()){
                if (reaccion.getIsLike()==1){
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
                            .username(pregunta.getUser().getUsername())
                            .name(pregunta.getUser().getName())
                            .build())
                            .reacciones(ReaccionResponse.builder().likes(preguntaLikes).dislikes(preguntaDislikes).build())
                    .build();
            preguntaResponses.add(preguntaResponse);

        }));

        return preguntaResponses;
=======
        List<PreguntaEntity> preguntaEntities = preguntaRepository.findAll();
        return preguntaEntities.stream().map((Util::preguntaEntityToPreguntaResponse)).toList();
>>>>>>> 60b10761f6cb34082b331ead0860f011713b4e69
    }

    public List<PreguntaResponse> obtenerPreguntasPorUsuario() {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        Optional<User> user = usuarioRepository.findByUsername(username);
        if (user.isEmpty())
			throw new UsernameNotFoundException(String.format("El usuario %s no existe", username));

<<<<<<< HEAD
        List<Pregunta> preguntas = preguntaRepository.findAll();
        List<PreguntaResponse> preguntaResponses = new ArrayList<>();

        preguntas.stream()
        .filter(pregunta -> pregunta.getUser().getUsername().equals(username))
        .forEach((pregunta -> {
            int preguntaLikes = 0;
            int preguntaDislikes = 0;
            for (Reaccion reaccion:pregunta.getReacciones()){
                if (reaccion.getIsLike()==1){
                    preguntaLikes++;
                }else {
                    preguntaDislikes++;
                }
            }

            PreguntaResponse preguntaResponse = PreguntaResponse.builder()
                    .id(pregunta.getId())
                    .titulo(pregunta.getTitulo())
                    .descripcion(pregunta.getDescripcion())
                    .tag(pregunta.getTag())
                    .user(UserResponse
                            .builder()
                            .username(pregunta.getUser().getUsername())
                            .name(pregunta.getUser().getName())
                            .build())
                            .reacciones(ReaccionResponse.builder().likes(preguntaLikes).dislikes(preguntaDislikes).build())
                    .build();
            preguntaResponses.add(preguntaResponse);

        }));

        return preguntaResponses;
=======
    public void eliminarPregunta(String idPregunta) {
        PreguntaEntity preguntaEntity = preguntaRepository.findById(idPregunta)
                .orElseThrow(() -> new BusinessException("idPregunta incorrecto"));
        UserEntity userAuthenticated = Util.getUserAuthenticated(userRepository);

        if (!Objects.equals(preguntaEntity.getUserEntity().getUsername(), userAuthenticated.getUsername())) {
            throw new BusinessException("Accion no permitida");
        }
        preguntaRepository.delete(preguntaEntity);
>>>>>>> 60b10761f6cb34082b331ead0860f011713b4e69
    }
}
