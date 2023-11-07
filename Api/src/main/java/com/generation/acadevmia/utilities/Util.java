package com.generation.acadevmia.utilities;

import com.generation.acadevmia.entity.PreguntaEntity;
import com.generation.acadevmia.entity.UserEntity;
import com.generation.acadevmia.exception.BusinessException;
import com.generation.acadevmia.payload.response.PreguntaResponse;
import com.generation.acadevmia.payload.response.ReaccionResponse;
import com.generation.acadevmia.payload.response.UserResponse;
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

    public static PreguntaResponse preguntaEntityToPreguntaResponse(PreguntaEntity preguntaEntity) {
        int likes = (int) preguntaEntity.getReacciones().stream().
                filter(reaccion -> reaccion.getIsLike() == 1).count();
        int dislikes = preguntaEntity.getReacciones().size() - likes;
        return PreguntaResponse.builder()
                .id(preguntaEntity.getId())
                .titulo(preguntaEntity.getTitulo())
                .descripcion(preguntaEntity.getDescripcion())
                .tag(preguntaEntity.getTag())
                .user(UserResponse
                        .builder()
                        .username(preguntaEntity.getUserEntity().getUsername())
                        .name(preguntaEntity.getUserEntity().getName())
                        .build())
                .reacciones(ReaccionResponse.builder().likes(likes).dislikes(dislikes).build())
                .build();
    }
}
