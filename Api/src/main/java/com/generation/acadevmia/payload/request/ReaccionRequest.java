package com.generation.acadevmia.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReaccionRequest {
    private int isLike;
    private EReaccionType tipo;
    private String id;

}
