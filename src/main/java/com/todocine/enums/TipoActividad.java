package com.todocine.enums;

public enum TipoActividad {
    FAVORITO_PELICULA(0, "FAVORITO_PELICULA"),
    PUNTUACION_PELICULA(1, "PUNTUACION_PELICULA"),
    VISTA_PELICULA(2, "VISTA_PELICULA"),
    COMENTARIO_PELICULA(30, "COMENTARIO_PELICULA"),
    RESPUESTA_COMENTARIO(4, "RESPUESTA_COMENTARIO"),
    VOTO_COMENTARIO(5, "VOTO_COMENTARIO"),
    CREACION_LISTA(6, "CREACION_LISTA"),
    PUBLICAR_LISTA(7, "PUBLICAR_LISTA"),
    NOMBRE_DESC_LISTA(8, "NOMBRE_DESC_LISTA"),
    NOMBRE_LISTA(9, "NOMBRE_LISTA"),
    DESCRIPCION_LISTA(10, "DESCRIPCION_LISTA"),
    BORRADA_LISTA(11, "BORRADA_LISTA"),
    MOVIE_LISTA(12, "MOVIE_LISTA"),
    VALORACION_LISTA(13, "VALORACION_LISTA");

    private final int value;
    private final String name;

    TipoActividad(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }


}
