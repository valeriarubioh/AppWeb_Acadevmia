package com.generation.acadevmia.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReaccionResponse {

    private int likes;
    private int dislikes;
    private EUserReaction userHasReacted;
}
