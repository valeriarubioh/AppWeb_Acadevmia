package com.generation.acadevmia.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetRespuestaResponse {

    private PreguntaResponse pregunta;
    private List<RespuestaResponse> respuestas;
}
