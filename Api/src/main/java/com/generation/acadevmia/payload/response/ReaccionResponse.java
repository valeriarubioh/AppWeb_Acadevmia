package com.generation.acadevmia.payload.response;

import com.generation.acadevmia.model.Reaccion;
import com.generation.acadevmia.model.Respuesta;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class ReaccionResponse {

    private int likes;
    private int dislikes;
}
