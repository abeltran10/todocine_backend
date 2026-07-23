package com.todocine.configuration;


public class Constants {

    //TOKEN
    public static final String HEADER_AUTHORIZACION_KEY = "Authorization";

    public static final String TOKEN_BEARER_PREFIX = "Bearer";

    public static long TOKEN_EXPIRATION_TIME= 60 * 60 * 1000;


    //EXCEPTIONS

    public static final String MOVIE_NOTFOUND = "No se ha encontrado la película";

    public static final String TMDB_ERROR = "La respuesta de TMDB ha fallado";

    public static final String GANADOR_EXISTS = "Ganador ya existe";

    public static final String PREMIO_NOTFOUND = "No se ha encontrado el premio";

    public static final String USER_PASSWORD_ERROR = "Usuario o contraseña incorrectos";

    public static final String CAPTCHA_MISSING = "Falta la verificación captcha en el cuerpo de la petición";

    public static final String USER_NOTFOUND = "No existe el usuario";

    public static final String USER_FORBIDDEN = "El usuario no es el de la sesión";

    public static final String USER_EXISTS = "Un usuario con ese nombre ya existe";

    public static final String CAPTCHA_UNAUTHORISED = "No autorizado ha registrarse. La validación captcha ha fallado";

    public static final String MOVIE_SEARCH_BADREQUEST = "Hay que indicar los filtros de busqueda";

    public static final String LISTA_NOT_FOUND = "Lista no encontrada";

    public static final String ID_NOT_MATCH = "El ID del path no coincide con el ID del cuerpo";


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

    //PAGINATION

    public static final int PAGE_SIZE = 21;
    public static final int MOVIELIST_PAGE_SIZE = 5;
    public static final int LISTAS_PAGE_SIZE = 10;
    public static final int FAVOURITES_SIZE = 12;
}
