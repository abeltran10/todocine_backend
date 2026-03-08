# Todo Cine

## What is Todo Cine

Todo Cine is a web app to manage movies from [TMDB](https://www.themoviedb.org/) 

## Code

Todo Cine is an API REST developed with [Java and Springboot in backend](https://github.com/abeltran10/todocine_backend) and there are two frontends one with [Javascript's react framework](https://github.com/abeltran10/todo_cine_frontend) and another with [Angular](https://github.com/abeltran10/todocine_front_angular). It's SQL database. I had to add hibernate-community-dialects in pom because my SQL server is no longer supported.

application.properties loads properties from three files, one per environment (prod, dev, test) and Constants file is missing in order you can create it for your need.


## Last release
- [v6.1.0](https://github.com/abeltran10/todocine_backend/releases/tag/v6.1.0)

## Install

- Download [last release](https://github.com/abeltran10/todocine_backend/releases/tag/v6.1.0) compressed file. 
- Add application.properties and Constants.java files to project.
- Execute [mvn clean install] command and deploy .jar file generated in one server.

## API de Todo Cine
API gestionada con Spring Boot, seguridad JWT y catálogo de películas con manejo de excepciones.

### Available authorizations
#### BearerAuth (HTTP, bearer)
Bearer format: JWT

---

### [GET] /usuarios
**Buscar usuarios por nombre**

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| username | query | Nombre de usuario para la búsqueda | Yes | string |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK - Lista de usuarios encontrada. | **application/json**: [ [UsuarioDTO](#usuariodto) ]<br> |
| 400 | Datos inválidos. |  |
| 401 | Token no válido. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

### [POST] /usuarios
**Insertar un nuevo usuario**

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [UsuarioDTO](#usuariodto)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 201 | Created - Usuario registrado con éxito. | **application/json**: [UsuarioDTO](#usuariodto)<br> |
| 400 | Datos inválidos. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

### [GET] /usuarios/{id}
**Obtener usuario por ID**

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| id | path | ID único del usuario | Yes | long |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK - Usuario encontrado. | **application/json**: [UsuarioDTO](#usuariodto)<br> |
| 403 | Acceso denegado. |  |
| 404 | No encontrado. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

### [PUT] /usuarios/{id}
**Actualizar usuario**

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| id | path | ID único del usuario | Yes | long |

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [UsuarioDTO](#usuariodto)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK - Usuario actualizado. | **application/json**: [UsuarioDTO](#usuariodto)<br> |
| 400 | Datos inválidos. |  |
| 403 | Acceso denegado. |  |
| 404 | No encontrado. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

---

### [GET] /usuarios/{userId}/movies
**Obtener películas del usuario (Paginado)**

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| userId | path | ID del usuario | Yes | long |
| vista | query | Filtrar por películas vistas (true/false) | Yes | string |
| votada | query | Filtrar por películas votadas | Yes | string |
| orderBy | query | Campo de ordenación (ej. popularity) | Yes | string |
| page | query | Número de página solicitado | Yes | integer |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK - Listado paginado obtenido. | **application/json**: [PaginatorMovieDetailDTO](#paginatormoviedetaildto)<br> |
| 403 | Acceso denegado. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

---

### [PUT] /usuarios/{userId}/movies/{movieId}
**Actualizar estado de una película para un usuario**

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| userId | path | ID del usuario | Yes | long |
| movieId | path | ID de la película | Yes | long |

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [UsuarioMovieDTO](#usuariomoviedto)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK - Estado de la película actualizado. | **application/json**: [MovieDetailDTO](#moviedetaildto)<br> |
| 400 | Datos inválidos. |  |
| 403 | Acceso denegado. |  |
| 404 | No encontrado. |  |
| 502 | Error en el servicio externo. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

---

### [GET] /movies
**Listado de películas con filtros (Paginado)**

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| name | query | Nombre de la película | Yes | string |
| status | query | Estado (Released, Post-Production...) | Yes | string |
| region | query | Región (ES, US...) | Yes | string |
| page | query | Número de página | Yes | integer |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Lista de películas filtrada. | **application/json**: [PaginatorMovieDTO](#paginatormoviedto)<br> |
| 400 | Datos inválidos. |  |
| 404 | No encontrado. |  |
| 502 | Error en el servicio externo. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

### [GET] /movies/{id}
**Obtener detalle completo de una película por ID**

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| id | path | ID de la película | Yes | long |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Detalle de la película. | **application/json**: [MovieDetailDTO](#moviedetaildto)<br> |
| 404 | No encontrado. |  |
| 502 | Error en servidor externo. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

---

### [GET] /ganadores
**Obtener ganadores por premio y año**

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| premioId | query | ID del premio | Yes | long |
| anyo | query | Año del premio | Yes | integer |
| pagina | query | Página de resultados | Yes | integer |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Listado de ganadores obtenido. | **application/json**: [PaginatorGanadorDTO](#paginatorganadordto)<br> |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

### [POST] /ganadores
**Insertar un nuevo ganador**

Requiere rol de ADMIN.

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [GanadorDTO](#ganadordto)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 201 | Ganador registrado. | **application/json**: [GanadorDTO](#ganadordto)<br> |
| 400 | Datos inválidos. |  |
| 403 | Permisos insuficientes. |  |
| 502 | Error en servidor externo. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

---

### [GET] /premios/{id}/categorias
**Obtener categorías de un premio**

Requiere rol de ADMIN.

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| id | path | ID del premio (ej. Oscars) | Yes | long |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Lista de categorías. | **application/json**: [ [CategoriaDTO](#categoriadto) ]<br> |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

---
### Schemas

#### UsuarioDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | long | Identificador único | No |
| username | string | Nombre de acceso | Yes |
| password | password | Clave de acceso | Yes |
| rol | string | Rol asignado (USER/ADMIN) | No |

#### PaginatorMovieDetailDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| page | integer | Página actual | No |
| results | [ [MovieDetailDTO](#moviedetaildto) ] | Lista de resultados detallados | No |
| total_pages | integer | Total de páginas | No |
| total_results | integer | Total de registros | No |

#### MovieDetailDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | long | ID de la película | No |
| original_title | string | Título original | No |
| title | string | Título traducido | No |
| poster_path | string | Ruta de la imagen del póster | No |
| overview | string | Resumen o sinopsis | No |
| release_date | string | Fecha de estreno | No |
| popularity | double | Índice de popularidad | No |
| vote_count | integer | Número total de votos externos | No |
| vote_average | double | Puntuación media externa | No |
| original_language | string | Idioma original | No |
| favoritos | boolean | Es favorita para el usuario | No |
| voto | double | Voto del usuario | No |
| vista | boolean | Película ya vista por el usuario | No |
| total_votos_TC | integer | Votos totales en Todo Cine | No |
| votos_media_TC | double | Media en Todo Cine | No |
| genres | [ [GenreDTO](#genredto) ] | Lista de géneros | No |
| videos | [ [VideoDTO](#videodto) ] | Lista de trailers o videos | No |

#### GenreDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | string | ID del género | Yes |
| name | string | Nombre del género | No |

#### VideoDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | string | Identificador del video | Yes |
| name | string | Nombre del video | No |
| key | string | Clave de plataforma (ej. YouTube ID) | No |
| site | string | Sitio de alojamiento | No |
| type | string | Tipo de video (Trailer, Teaser) | No |

#### UsuarioMovieDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| usuarioId | long | ID del usuario | Yes |
| movieId | long | ID de la película | Yes |
| favoritos | boolean | Estado de favorito | Yes |
| vista | boolean | Estado de vista | Yes |
| voto | double | Puntuación personal | No |

#### PaginatorMovieDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| page | integer | Página actual | No |
| results | [ [MovieDTO](#moviedto) ] | Lista de películas | No |
| total_pages | integer | Páginas totales | No |
| total_results | integer | Registros totales | No |

#### MovieDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | long | ID único | Yes |
| original_title | string | Título original | No |
| title | string | Título | Yes |
| poster_path | string | Imagen | No |
| overview | string | Sinopsis | No |
| release_date | string | Fecha estreno | No |
| popularity | double | Popularidad | No |
| vote_count | integer | Votos totales | No |
| vote_average | double | Media votos | No |
| original_language | string | Idioma | No |
| genres | [ [GenreDTO](#genredto) ] | Géneros asociados | No |
| videos | [ [VideoDTO](#videodto) ] | Videos asociados | No |
| total_votos_TC | integer | Votos locales | No |
| votos_media_TC | double | Media local | No |

#### PaginatorGanadorDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| page | integer | Página actual | No |
| results | [ [GanadorDTO](#ganadordto) ] | Lista de ganadores | No |
| total_pages | integer | Páginas totales | No |
| total_results | integer | Registros totales | No |

#### GanadorDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| premioId | long | ID del premio | Yes |
| premio | string | Nombre del premio | No |
| categoriaId | long | ID de la categoría | Yes |
| categoria | string | Nombre de la categoría | No |
| anyo | integer | Año de la gala | Yes |
| movieId | long | ID de la película | Yes |
| original_title | string | Título original | No |
| title | string | Título | No |
| poster_path | string | Imagen póster | No |
| overview | string | Sinopsis | No |
| release_date | string | Fecha de lanzamiento | No |
| popularity | double | Popularidad | No |
| vote_count | integer | Votos | No |
| vote_average | double | Nota media | No |
| original_language | string | Idioma | No |
| total_votos_TC | integer | Votos locales | No |
| votos_media_TC | double | Media local | No |
| genres | [ [GenreDTO](#genredto) ] | Lista de géneros | No |
| videos | [ [VideoDTO](#videodto) ] | Lista de videos | No |

#### CategoriaDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | long | ID único de la categoría | No |
| nombre | string | Descripción de la categoría | No |


## Entity-Relation Diagram

## Demo

Demo v6.1.0 release

[TodoCine-Angular[v6.1.0].webm](https://github.com/user-attachments/assets/14ba5b37-85af-4e68-a30a-d3b8c93e9754)





















