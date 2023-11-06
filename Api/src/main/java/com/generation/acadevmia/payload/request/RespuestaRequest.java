package com.generation.acadevmia.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RespuestaRequest {
   @NotBlank
   @Size(max = 500)
   private String texto;

   @NotBlank
   @Size(max = 500)
   private String codigo;

}
