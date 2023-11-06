package com.generation.acadevmia.controller;

import com.generation.acadevmia.model.Pregunta;
import com.generation.acadevmia.model.Respuesta;
import com.generation.acadevmia.payload.request.RespuestaRequest;
import com.generation.acadevmia.payload.response.PreguntaResponse;
import com.generation.acadevmia.payload.response.RespuestaResponse;
import com.generation.acadevmia.service.PreguntaService;
import com.generation.acadevmia.service.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/respuestas")
public class RespuestaController {

    @Autowired
    RespuestaService respuestaService;

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<RespuestaResponse> crearRespuesta(@RequestBody RespuestaRequest respuesta, @PathVariable String id){
        return new ResponseEntity(respuestaService.crearRespuesta(respuesta, id), HttpStatus.CREATED);
    }
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Respuesta marcarFavorito(@RequestBody Respuesta respuesta, @PathVariable String id){
        return respuestaService.marcarFavorito(respuesta,id);
    }

    @GetMapping("/{idPregunta}")
    public ResponseEntity<List<RespuestaResponse>> obtenerRespuestas(@PathVariable String idPregunta) {
        List<RespuestaResponse> respuestaResponse = respuestaService.obtenerRespuestas(idPregunta);
        return ResponseEntity.ok(respuestaResponse);
    }
}
