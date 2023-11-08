package com.generation.acadevmia.payload.request;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PreguntaRequest {
   @NotBlank
   @Size(min = 1, max = 100)
   private String titulo;

   @NotBlank
   @Size(min = 1, max = 1000)
   private String descripcion;

   @Size(max = 80)
   private String tag;

}
