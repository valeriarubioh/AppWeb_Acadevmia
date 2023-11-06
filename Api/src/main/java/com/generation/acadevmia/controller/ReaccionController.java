package com.generation.acadevmia.controller;

import com.generation.acadevmia.model.Pregunta;
import com.generation.acadevmia.model.Reaccion;
import com.generation.acadevmia.service.PreguntaService;
import com.generation.acadevmia.service.ReaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/reacciones")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class ReaccionController {

    @Autowired
    ReaccionService reaccionService;

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Pregunta crearReaccion(@RequestBody Reaccion reaccion, @PathVariable String id) {
        return reaccionService.crearReaccion(reaccion, id);
    }
}
