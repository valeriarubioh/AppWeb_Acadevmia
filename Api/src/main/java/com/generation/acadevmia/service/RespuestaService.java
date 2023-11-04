package com.generation.acadevmia.service;

import com.generation.acadevmia.model.Pregunta;
import com.generation.acadevmia.model.Respuesta;
import com.generation.acadevmia.model.User;
import com.generation.acadevmia.payload.response.ReaccionResponse;
import com.generation.acadevmia.payload.response.RespuestaResponse;
import com.generation.acadevmia.repository.PreguntaRepository;
import com.generation.acadevmia.repository.ReaccionRepository;
import com.generation.acadevmia.repository.RespuestaRepository;
import com.generation.acadevmia.repository.UserRepository;
import com.generation.acadevmia.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Respuesta crearRespuesta(Respuesta respuesta, String id) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = usuarioRepository.findByUsername(principal.getUsername());
        respuesta.setUser(user.get());
        respuesta.setReacciones(new ArrayList<>());
        respuesta.setFavorito(false);
        return respuestaRepository.save(respuesta);

        /*Optional<Pregunta> preguntaOptional = preguntaRepository.findById(id);
        if(preguntaOptional.isPresent()){
            Respuesta savedResponse = respuestaRepository.save(respuesta);
            Pregunta pregunta= preguntaOptional.get();
            pregunta.getRespuestas().add(savedResponse);
            Pregunta preguntaSaved = preguntaRepository.save(pregunta);
            return respuestaRepository.save(respuesta);
        }else {
            return null;
        }*/
    }
    public Respuesta marcarFavorito(Respuesta respuesta,String id){
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = usuarioRepository.findByUsername(principal.getUsername());
        Optional<Pregunta> preguntaOptional = preguntaRepository.findById(id);
        if(user.isPresent() && preguntaOptional.isPresent()){
            String username = user.get().getUsername();
            String userQuestion = String.valueOf(preguntaOptional.get().getUser());
            if(Objects.equals(username, userQuestion)){
                respuesta.setFavorito(true);
            }
        }
        return respuesta;
    }
    public List<RespuestaResponse> obtenerRespuestas() {
        List<Respuesta> respuestas = respuestaRepository.findAll();
        List<RespuestaResponse> respuestaResponses = new ArrayList<>();
        respuestas.forEach((respuesta -> {
            int likes= (int) respuesta.getReacciones().stream().
                    filter(reaccion -> reaccion.getIsLike()==1).count();
            int dislikes = respuesta.getReacciones().size()-likes;
            RespuestaResponse respuestaResponse = RespuestaResponse.builder()
                    .id(respuesta.getId())
                    .texto(respuesta.getTexto())
                    .codigo(respuesta.getCodigo())
                    .reacciones(ReaccionResponse.builder().likes(likes).dislikes(dislikes).build())
                    .build();
            respuestaResponses.add(respuestaResponse);
        }));
        return respuestaResponses;
    }
}
