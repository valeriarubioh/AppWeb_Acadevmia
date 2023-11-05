package com.generation.acadevmia.controller;

import com.generation.acadevmia.entity.PreguntaEntity;
import com.generation.acadevmia.entity.ReaccionEntity;
import com.generation.acadevmia.service.ReaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> crearReaccion(@RequestBody ReaccionEntity reaccionEntity, @PathVariable String id) {
        reaccionService.crearReaccion(reaccionEntity, id);
        return ResponseEntity.noContent().build();
    }
}
