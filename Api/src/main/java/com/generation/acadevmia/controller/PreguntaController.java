package com.generation.acadevmia.controller;

import com.generation.acadevmia.payload.request.PreguntaRequest;
import com.generation.acadevmia.payload.response.PreguntaResponse;
import com.generation.acadevmia.service.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/v1/preguntas")
public class PreguntaController {

    @Autowired
    PreguntaService preguntaService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<PreguntaResponse> crearPregunta(@RequestBody Pregunta pregunta) {
        return new ResponseEntity<PreguntaResponse>(preguntaService.crearPregunta(pregunta), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PreguntaResponse>> obtenerPreguntas() {
        List<PreguntaResponse> preguntaResponses = preguntaService.obtenerPreguntas();
        return ResponseEntity.ok(preguntaResponses);
    }

    @DeleteMapping("/{idPregunta}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarPregunta(@PathVariable String idPregunta) {
        preguntaService.eliminarPregunta(idPregunta);
        return ResponseEntity.noContent().build();
    }
}
