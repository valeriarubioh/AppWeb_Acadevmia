package com.generation.acadevmia.utilities;

import com.generation.acadevmia.entity.UserEntity;
import com.generation.acadevmia.exception.BusinessException;
import com.generation.acadevmia.repository.UserRepository;
import com.generation.acadevmia.security.services.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class Util {

    private Util() {
        throw new IllegalStateException("Utility class");
    }

    public static UserEntity getUserAuthenticated(UserRepository userRepository) {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
            .orElseThrow(() -> new BusinessException("User authenticated not found"));
    }
}
