package com.generation.acadevmia.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.acadevmia.model.User;
import com.generation.acadevmia.payload.request.UpdateNameRequest;
import com.generation.acadevmia.service.UsuarioService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;

	@GetMapping("/getName")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, String>> obtenerNombre() {
		Map<String, String> m = new HashMap<>();
		m.put("name", usuarioService.getName());
		return ResponseEntity.ok(m);
    }

	@PutMapping("/updateName")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, String>> actualizarNombre(@Valid @RequestBody UpdateNameRequest request) {
		Map<String, String> m = new HashMap<>();
		User u = usuarioService.updateName(request.getNewName());
		if (u == null) {
			m.put("message", "Hubo un problema al actualizar el nombre del usuario");
		} else {
			m.put("message", String.format("Se actualizó con éxito el nombre del usuario con id %s", u.getId()));
		}
		return ResponseEntity.ok(m);
    }
}
