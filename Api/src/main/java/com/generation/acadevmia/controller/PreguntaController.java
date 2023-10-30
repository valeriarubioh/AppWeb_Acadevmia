package com.generation.acadevmia.controller;

import com.generation.acadevmia.model.Pregunta;
import com.generation.acadevmia.service.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/preguntas")
public class PreguntaController {

    @Autowired
    PreguntaService preguntaService;

    @PostMapping
    public Pregunta crearPregunta(@RequestBody Pregunta pregunta) {
        return preguntaService.crearPregunta(pregunta);
    }

    @GetMapping
    public List<Pregunta> obtenerPreguntas() {
        return preguntaService.obtenerPreguntas();
    }
}
