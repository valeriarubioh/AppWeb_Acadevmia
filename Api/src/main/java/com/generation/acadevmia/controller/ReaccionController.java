package com.generation.acadevmia.controller;

import com.generation.acadevmia.payload.request.ReaccionRequest;
import com.generation.acadevmia.service.ReaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/reacciones")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class ReaccionController {

    @Autowired
    ReaccionService reaccionService;

    @PostMapping()
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> crearReaccion(@RequestBody ReaccionRequest reaccionRequest) {
        reaccionService.crearReaccion(reaccionRequest);
        return ResponseEntity.noContent().build();
    }
}
