package com.generation.acadevmia.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespuestaResponse {

    private String id;
    private String texto;
    private String codigo;
    private Boolean favorito;
    private UserResponse user;
    private ReaccionResponse reacciones;
}
