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

| Code | Description |
| ---- | ----------- |
| 201 | Created - Usuario registrado con éxito. |
| 400 | Datos inválidos. |

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

| Code | Description |
| ---- | ----------- |
| 200 | OK - Usuario actualizado. |
| 400 | Datos inválidos. |
| 403 | Acceso denegado. |
| 404 | No encontrado. |

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
| orderBy | query | Campo de ordenación | Yes | string |
| page | query | Número de página | Yes | integer |

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

| Code | Description |
| ---- | ----------- |
| 201 | Ganador registrado. |
| 403 | Permisos insuficientes. |

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
| id | path | ID del premio | Yes | long |

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
| id | long |  | No |
| username | string |  | Yes |
| password | password |  | Yes |
| rol | string |  | No |

#### PaginatorMovieDetailDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| page | integer |  | No |
| results | [ [MovieDetailDTO](#moviedetaildto) ] |  | No |
| total_pages | integer |  | No |
| total_results | integer |  | No |

#### MovieDetailDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | long |  | No |
| original_title | string |  | No |
| title | string |  | No |
| poster_path | string |  | No |
| overview | string |  | No |
| release_date | string |  | No |
| popularity | double |  | No |
| vote_count | integer |  | No |
| vote_average | double |  | No |
| original_language | string |  | No |
| favoritos | boolean |  | No |
| voto | double |  | No |
| vista | boolean |  | No |
| total_votos_TC | integer |  | No |
| votos_media_TC | double |  | No |
| genres | [ [GenreDTO](#genredto) ] |  | No |
| videos | [ [VideoDTO](#videodto) ] |  | No |

#### GenreDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | string | ID del género | Yes |
| name | string | Nombre del género | No |

#### VideoDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | string |  | Yes |
| name | string |  | No |
| key | string |  | No |
| site | string |  | No |
| type | string |  | No |

#### UsuarioMovieDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| usuarioId | long | ID usuario | Yes |
| movieId | long | ID película | Yes |
| favoritos | boolean |  | Yes |
| vista | boolean |  | Yes |
| voto | double |  | No |

#### PaginatorMovieDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| page | integer |  | No |
| results | [ [MovieDTO](#moviedto) ] |  | No |
| total_pages | integer |  | No |
| total_results | integer |  | No |

#### MovieDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | long |  | Yes |
| original_title | string |  | No |
| title | string |  | Yes |
| poster_path | string |  | No |
| overview | string |  | No |
| release_date | string |  | No |
| popularity | double |  | No |
| vote_count | integer |  | No |
| vote_average | double |  | No |
| original_language | string |  | No |
| genres | [ [GenreDTO](#genredto) ] |  | No |
| videos | [ [VideoDTO](#videodto) ] |  | No |
| total_votos_TC | integer |  | No |
| votos_media_TC | double |  | No |

#### PaginatorGanadorDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| page | integer |  | No |
| results | [ [GanadorDTO](#ganadordto) ] |  | No |
| total_pages | integer |  | No |
| total_results | integer |  | No |

#### GanadorDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| premioId | long |  | Yes |
| premio | string |  | No |
| categoriaId | long |  | Yes |
| categoria | string |  | No |
| anyo | integer |  | Yes |
| movieId | long |  | Yes |
| original_title | string |  | No |
| title | string |  | No |
| poster_path | string |  | No |
| overview | string |  | No |
| release_date | string |  | No |
| popularity | double |  | No |
| vote_count | integer |  | No |
| vote_average | double |  | No |
| original_language | string |  | No |
| total_votos_TC | integer |  | No |
| votos_media_TC | double |  | No |
| genres | [ [GenreDTO](#genredto) ] |  | No |
| videos | [ [VideoDTO](#videodto) ] |  | No |

#### CategoriaDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | long | ID categoría | No |
| nombre | string | Nombre categoría | No |


## Demo

Demo v6.1.0 release

[TodoCine-Angular[v6.1.0].webm](https://github.com/user-attachments/assets/14ba5b37-85af-4e68-a30a-d3b8c93e9754)





















