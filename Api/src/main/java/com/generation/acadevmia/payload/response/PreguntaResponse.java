package com.generation.acadevmia.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PreguntaResponse {

    private String id;
    private String titulo;
    private String descripcion;
    private String tag;
    private UserResponse user;
//    private ArrayList<RespuestaResponse> respuestas;
    private ReaccionResponse reacciones;
}
