package com.generation.acadevmia.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateNameRequest {

	@NotBlank
	private String newName;
}
