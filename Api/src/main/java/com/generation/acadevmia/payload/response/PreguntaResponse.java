package com.generation.acadevmia.payload.response;

import com.generation.acadevmia.model.Reaccion;
import com.generation.acadevmia.model.Respuesta;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
@Data
@Builder
public class PreguntaResponse {

    private String id;
    private String titulo;
    private String descripcion;
    private String tag;
    private UserResponse user;
    private ArrayList<RespuestaResponse> respuestas;
    private ReaccionResponse reacciones;
}
