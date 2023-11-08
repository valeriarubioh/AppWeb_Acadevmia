package com.generation.acadevmia.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProfilePictureRequest {

	@NotBlank
	private String profilePicture;
}
