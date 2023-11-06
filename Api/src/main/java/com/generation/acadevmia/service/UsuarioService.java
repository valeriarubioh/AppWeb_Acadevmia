package com.generation.acadevmia.service;

import com.generation.acadevmia.model.User;
import com.generation.acadevmia.repository.RoleRepository;
import com.generation.acadevmia.repository.UserRepository;
import com.generation.acadevmia.security.jwt.JwtUtils;
import com.generation.acadevmia.security.services.UserDetailsImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public String getName() {
		String result = null;
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> u = userRepository.findByUsername(principal.getUsername());
		if (u.isPresent())
			return u.get().getName();
		return result;
    }

    public User updateName(String newName) {
		UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> u = userRepository.findByUsername(principal.getUsername());
		if (u.isPresent()) {
			User user = u.get();
			user.setName(newName);
			return userRepository.save(user);
		}
		return null;
    }
}
