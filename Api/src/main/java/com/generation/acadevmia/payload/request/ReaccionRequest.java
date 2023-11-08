package com.generation.acadevmia.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReaccionRequest {
    @NotBlank
    private int isLike;
    @NotBlank
    private EReaccionType tipo;
    @NotBlank
    private String id;

}
