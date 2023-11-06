package com.generation.acadevmia.payload.request;

public enum EReaccionType {

    PREGUNTA("PREGUNTA"),
    RESPUESTA("RESPUESTA");

    private final String text;

    EReaccionType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ReaccionType{" +
                "text='" + text + '\'' +
                '}';
    }
}
