package com.generation.acadevmia.payload.response;

import com.generation.acadevmia.model.Reaccion;
import com.generation.acadevmia.model.Respuesta;
import com.generation.acadevmia.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
