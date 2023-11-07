package com.generation.acadevmia.controller;

import com.generation.acadevmia.payload.request.RespuestaRequest;
import com.generation.acadevmia.payload.response.GetRespuestaResponse;
import com.generation.acadevmia.payload.response.RespuestaResponse;
import com.generation.acadevmia.service.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<RespuestaResponse> crearRespuesta(@RequestBody RespuestaRequest respuesta, @PathVariable String id) {
        return new ResponseEntity(respuestaService.crearRespuesta(respuesta, id), HttpStatus.CREATED);
    }

    @PatchMapping("/{idRespuesta}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<RespuestaResponse> marcarFavorito(@PathVariable String idRespuesta) {
        return ResponseEntity.ok(respuestaService.marcarFavorito(idRespuesta));
    }

    @GetMapping("/{idPregunta}")
    public ResponseEntity<GetRespuestaResponse> obtenerRespuestas(@PathVariable String idPregunta) {
        return ResponseEntity.ok(respuestaService.obtenerRespuestas(idPregunta));
    }
}
