package com.generation.acadevmia.payload.response;

public enum EUserReaction {
    LIKE("LIKE"),
    DISLIKE("DISLIKE"),
    NONE("NONE");
    private final String text;

    EUserReaction(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "EUserReaction{" +
                "text='" + text + '\'' +
                '}';
    }
}
