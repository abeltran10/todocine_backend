package com.todocine.configuration;


public class Constants {

    //TOKEN
    public static final String HEADER_AUTHORIZACION_KEY = "Authorization";

    public static final String TOKEN_BEARER_PREFIX = "Bearer";

    public static long TOKEN_EXPIRATION_TIME= 60 * 60 * 1000;


    //EXCEPTIONS

    public static final String MOVIE_NOTFOUND = "No se ha encontrado la película";

    public static final String TMDB_ERROR = "La respuesta de TMDB ha fallado";

    public static final String CARTELERA_NOTFOUND = "No se ha encontrado la cartelera para esa región";

    public static final String GANADORES_NOTFOUND = "Ganadores no encontrados";

    public static final String GANADOR_EXISTS = "Ganador ya existe";

    public static final String PREMIO_NOTFOUND = "No se ha encontrado el premio";

    public static final String USER_PASSWORD_ERROR = "Usuario o contraseña incorrectos";

    public static final String PASSWORD_MISSING = "La constraseña está vacía";

    public static final String USER_NOTFOUND = "No existe el usuario";

    public static final String USER_FORBIDDEN = "El usuario no es el de la sesión";

    public static final String USER_EXISTS = "Un usuario con ese nombre ya existe";

    public static final String FAVORITOS_NOTFOUND = "No se ha encontrado la película en favoritos";

    public static final String MOVIE_SEARCH_BADREQUEST = "Hay que indicar los filtros de busqueda";

    public static final String MOVIE_EXISTS = "La película ya está insertada";

    public static final String CATEGORIA_NOTFOUND = "No se ha encontrado la categoria";

    public static final String LISTA_NOT_FOUND = "Lista no encontrada";

    public static final String ID_NOT_MATCH = "El ID del path no coincide con el ID del cuerpo";

    public static final String VALORATION_EXISTS = "El usuario ya ha valorado esta lista";


    //FILTERS FAVORITOS

    public static final String VISTA_FILTER = "VISTA";

    public static final String VOTADA_FILTER = "VOTADA";

    //MOVIES FILTERS

    public static final String MOVIE_NAME = "NAME";
    public static final String MOVIE_STATUS = "STATUS";
    public static final String MOVIE_REGION = "REGION";

    //ORDERS

    public static final String ORDER_TITULO = "TITLE";
    public static final String ORDER_ANYO = "YEAR";
}
