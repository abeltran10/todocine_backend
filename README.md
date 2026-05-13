# Todo Cine

## What is Todo Cine

Todo Cine is a web app to manage movies from [TMDB](https://www.themoviedb.org/) 

## Code

Todo Cine is an API REST developed with [Java and Springboot in backend](https://github.com/abeltran10/todocine_backend) and there are two frontends one with [Javascript's react framework](https://github.com/abeltran10/todo_cine_frontend) and another with [Angular](https://github.com/abeltran10/todocine_front_angular). It's SQL database. I had to add hibernate-community-dialects in pom because my Oracle SQL server is no longer supported.

application.properties loads properties from three files, one per environment (prod, dev, test) and Constants file is missing in order you can create it for your need.


## Last release
- [v6.3.5](https://github.com/abeltran10/todocine_backend/releases/tag/v6.3.5)

## Install

- Download [last release](https://github.com/abeltran10/todocine_backend/releases/tag/v6.3.5) compressed file. 
- Add application.properties and Constants.java files to project.
- Execute [mvn clean install] command and deploy .jar file generated in one server.

## Version: v6.3.5

### Available authorizations
#### BearerAuth (HTTP, bearer)
Bearer format: JWT

---

### [POST] /usuarios
**Insert a new user**

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [UsuarioReqDTO](#usuarioreqdto)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 201 | Created - User registered successfully. | **application/json**: [UsuarioDTO](#usuariodto)<br> |
| 400 | Invalid data. |  |
| 409 | Resource already exists. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

### [GET] /usuarios/{id}
**Get user by ID**

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| id | path | Unique user ID | Yes | long |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK - User found. | **application/json**: [UsuarioDTO](#usuariodto)<br> |
| 400 | Invalid data. |  |
| 403 | Access denied. |  |
| 404 | Not found. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

### [PUT] /usuarios/{id}
**Update user**

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| id | path | Unique user ID | Yes | long |

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [UsuarioReqDTO](#usuarioreqdto)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK - User updated. | **application/json**: [UsuarioDTO](#usuariodto)<br> |
| 400 | Invalid data. |  |
| 403 | Access denied. |  |
| 404 | Not found. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

---

### [GET] /usuarios/{userId}/movies
**Get user movies (Paginated)**

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| userId | path | User ID | Yes | long |
| vista | query | Filter by watched movies (true/false) | Yes | string |
| votada | query | Filter by voted movies | Yes | string |
| orderBy | query | Sorting field (e.g., popularity) | Yes | string |
| page | query | Requested page number | Yes | integer |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK - Paginated list retrieved. | **application/json**: [Paginator](#paginator)<br> |
| 400 | Invalid data. |  |
| 403 | Access denied. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

### [PUT] /usuarios/{userId}/movies/{movieId}
**Update movie status for a user**

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| userId | path | User ID | Yes | long |
| movieId | path | Movie ID | Yes | long |

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [UsuarioMovieDTO](#usuariomoviedto)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK - Movie status updated. | **application/json**: [MovieDetailDTO](#moviedetaildto)<br> |
| 400 | Invalid data. |  |
| 403 | Access denied. |  |
| 404 | Not found. |  |
| 502 | External server error. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

---

### [GET] /movies
**List movies with filters (Paginated)**

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| name | query | Movie title | Yes | string |
| status | query | Status (Released, Post-Production...) | Yes | string |
| region | query | Region code (ES, US...) | Yes | string |
| page | query | Page number | Yes | integer |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Filtered movie list. | **application/json**: [Paginator](#paginator)<br> |
| 400 | Invalid data. |  |
| 502 | External server error. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

### [GET] /movies/{id}
**Get full movie detail by ID**

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| id | path | Movie ID | Yes | long |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Movie detail. | **application/json**: [MovieDetailDTO](#moviedetaildto)<br> |
| 400 | Invalid data. |  |
| 404 | Not found. |  |
| 502 | External server error. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

---

### [GET] /ganadores
**Get winners by award and year**

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| premioId | query | Award ID | Yes | long |
| anyo | query | Award year | Yes | integer |
| pagina | query | Results page | Yes | integer |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | List of winners retrieved. | **application/json**: [Paginator](#paginator)<br> |
| 400 | Invalid data. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

### [POST] /ganadores
**Insert a new winner**

Requires ADMIN role.

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [GanadorReqDTO](#ganadorreqdto)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 201 | Winner registered. | **application/json**: [GanadorDTO](#ganadordto)<br> |
| 400 | Invalid data. |  |
| 403 | Access denied. |  |
| 409 | Resource already exists. |  |
| 502 | External server error. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

---

### [GET] /premios
**Get awards**

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | List of awards. | **application/json**: [ [PremioDTO](#premiodto) ]<br> |
| 400 | Invalid data. |  |
| 403 | Access denied. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

---

### [GET] /premios/{id}
**Get specific award**

Get award by unique identifier

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK-Award. | **application/json**: [PremioDTO](#premiodto)<br> |
| 400 | Invalid data. |  |
| 403 | Access denied. |  |
| 404 | Not found. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

---

### [GET] /premios/{id}/categorias
**Get categories for a specific award**

Requires ADMIN role.

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| id | path | Award ID (e.g., Oscars) | Yes | long |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | List of categories. | **application/json**: [ [CategoriaDTO](#categoriadto) ]<br> |
| 400 | Invalid data. |  |
| 403 | Access denied. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

---

### [GET] /usuarios/{userId}/listas
**Get user's lists of movies**

Returns a paginated list of all movie lists created by a specific user.

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| page | query | Page index (1..N) | Yes | integer |
| userId | path | Unique identifier of the user who owns the lists | Yes | long |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Paginated lists retrieved successfully | **application/json**: [Paginator](#paginator)<br> |
| 400 | Invalid data. |  |
| 403 | Access denied. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

### [POST] /usuarios/{userId}/listas
**Create a new list of movies**

Registers a new list of movies associated with the specified user.

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| userId | path | Unique identifier of the user who owns the lists | Yes | long |

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [ListaReqDTO](#listareqdto)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 201 | List created successfully | **application/json**: [ListaDTO](#listadto)<br> |
| 400 | Invalid data. |  |
| 403 | Access denied. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

### [PUT] /usuarios/{userId}/listas/{id}
**Update an existing list**

Updates the name, description, or the entire movie collection of a specific list.

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| userId | path |  | Yes | long |
| id | path | Unique identifier of the specific list of movies | Yes | long |

#### Request Body

| Required | Schema |
| -------- | ------ |
|  Yes | **application/json**: [ListaReqDTO](#listareqdto)<br> |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | List updated successfully | **application/json**: [ListaDTO](#listadto)<br> |
| 400 | Invalid data. |  |
| 403 | Access denied. |  |
| 404 | Not found. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

### [DELETE] /usuarios/{userId}/listas/{id}
**Delete a list of movies**

Permanently removes the list from the user's profile.

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| userId | path |  | Yes | long |
| id | path | Unique identifier of the specific list of movies | Yes | long |

#### Responses

| Code | Description |
| ---- | ----------- |
| 204 | List deleted successfully (No Content) |
| 400 | Invalid data. |
| 403 | Access denied. |
| 404 | Not found. |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

### [POST] /usuarios/{userId}/listas/{listaId}/movies/{movieId}
**Add movie to list**

Adds a specific movie to the list. If the movie does not exist in the local database, it will be persisted.

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| userId | path |  | Yes | long |
| listaId | path |  | Yes | long |
| movieId | path |  | Yes | long |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Movie added successfully. Returns the updated list. | **application/json**: [ListaDTO](#listadto)<br> |
| 400 | Invalid data. |  |
| 403 | Access denied. |  |
| 404 | Not found. |  |
| 502 | External server error. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

### [DELETE] /usuarios/{userId}/listas/{listaId}/movies/{movieId}
**Remove movie from list**

Removes the relationship between the movie and the list without deleting the movie from the global catalog.

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| userId | path |  | Yes | long |
| listaId | path |  | Yes | long |
| movieId | path |  | Yes | long |

#### Responses

| Code | Description |
| ---- | ----------- |
| 204 | Movie removed from list successfully |
| 400 | Invalid data. |
| 403 | Access denied. |
| 404 | Not found. |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

---

### [GET] /listas
**Get public lists of movies**

Returns a paginated list of all public movie lists.

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| page | query | Page index (1..N) | Yes | integer |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Paginated lists retrieved successfully | **application/json**: [Paginator](#paginator)<br> |
| 400 | Invalid data. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

---

### [GET] /listas/{id}
**Get details of the movie list**

Returns the full details of a specific list, including its metadata and movie collection.

#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ------ |
| id | path | Unique identifier of the specific list of movies | Yes | long |

#### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | List details retrieved successfully | **application/json**: [ListaDTO](#listadto)<br> |
| 400 | Invalid data. |  |
| 404 | Not found. |  |

##### Security

| Security Schema | Scopes |
| --------------- | ------ |
| BearerAuth |  |

---
### Schemas

#### UsuarioReqDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | long | Unique identifier | No |
| username | string | Access username | Yes |
| password | string (string) | Access password | Yes |
| rol | string | Assigned role (USER/ADMIN) | No |

#### UsuarioDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | long | Unique identifier | No |
| username | string | Access username | Yes |
| rol | string | Assigned role (USER/ADMIN) | Yes |

#### Paginator

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| page | integer | Current page | Yes |
| results | [  ] | List of results | No |
| total_pages | integer | Total pages | Yes |
| total_results | integer | Total records | Yes |

#### MovieDetailDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | long | Movie ID | Yes |
| original_title | string | Original title | Yes |
| title | string | Translated title | Yes |
| poster_path | string | Poster image path | Yes |
| overview | string | Summary or synopsis | No |
| release_date | string | Release date | Yes |
| popularity | double | Popularity index | Yes |
| vote_count | integer | Total external votes | Yes |
| vote_average | double | Average external score | Yes |
| original_language | string | Original language | Yes |
| favoritos | boolean | Is marked as favorite by user | No |
| voto | double | User's personal vote | No |
| vista | boolean | Already watched by user | No |
| total_votos_TC | integer | Total votes in Todo Cine | Yes |
| votos_media_TC | double | Average score in Todo Cine | Yes |
| genres | [ [GenreDTO](#genredto) ] | Genre list | Yes |
| videos | [ [VideoDTO](#videodto) ] | Trailers or video list | No |

#### GenreDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | string | Genre ID | Yes |
| name | string | Genre name | No |

#### VideoDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | string | Video identifier | Yes |
| name | string | Video name | Yes |
| key | string | Platform key (e.g., YouTube ID) | Yes |
| site | string | Hosting site | Yes |
| type | string | Video type (Trailer, Teaser) | Yes |

#### UsuarioMovieDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| usuarioId | long | User ID | Yes |
| movieId | long | Movie ID | Yes |
| favoritos | boolean | Favorite status | Yes |
| vista | boolean | Watch status | Yes |
| voto | double | Personal score | No |

#### MovieDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | long | Unique ID | Yes |
| original_title | string | Original title | Yes |
| title | string | Title | Yes |
| poster_path | string | Image path | Yes |
| overview | string | Synopsis | No |
| release_date | string | Release date | Yes |
| popularity | double | Popularity | No |
| vote_count | integer | Total votes | No |
| vote_average | double | Average score | No |
| original_language | string | Language | No |
| genres | [ [GenreDTO](#genredto) ] | Associated genres | No |
| videos | [ [VideoDTO](#videodto) ] | Associated videos | No |
| total_votos_TC | integer | Local votes | No |
| votos_media_TC | double | Local average | No |

#### GanadorReqDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| premioId | long | Award ID | Yes |
| categoriaId | long | Category ID | Yes |
| anyo | integer | Gala year | Yes |
| movieId | long | Movie ID | Yes |

#### GanadorDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| premioId | long | Award ID | Yes |
| premio | string | Award name | No |
| categoriaId | long | Category ID | Yes |
| categoria | string | Category name | No |
| anyo | integer | Gala year | Yes |
| movieId | long | Movie ID | Yes |
| original_title | string | Original title | No |
| title | string | Title | No |
| poster_path | string | Poster image | No |
| overview | string | Synopsis | No |
| release_date | string | Release date | No |

#### CategoriaDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | long | Unique category ID | Yes |
| nombre | string | Category description | Yes |

#### PremioDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | long | Unique award ID | Yes |
| titulo | string | Award name | Yes |
| anyos | [ integer ] | Award years | No |

#### ListaDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | long | Unique list ID | Yes |
| nombre | string | List name | Yes |
| descripcion | string | List description | Yes |
| username | string | List owner | Yes |
| publica | boolean | List visibility | Yes |
| movies | [ [MovieDTO](#moviedto) ] | Movies in the list | No |

#### ListaReqDTO

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | long | Unique list ID | No |
| nombre | string | List name | Yes |
| descripcion | string | List description | Yes |
| username | string | List owner | Yes |
| publica | boolean | List visibility | Yes |
| movies | [ [MovieDTO](#moviedto) ] | Movies in the list | No |



## Entity-Relation Diagram

<img width="1556" height="725" alt="entity-relation_diagram" src="https://github.com/user-attachments/assets/81b213af-11ec-4ed3-9759-c64c15d4883b" />



## UML

### Use case diagram

<img width="871" height="1655" alt="case_use_diagram" src="https://github.com/user-attachments/assets/e1c63124-c5e3-446e-9291-30189e606e60" />



### Classes diagram

<img width="5148" height="466" alt="classes_diagram" src="https://github.com/user-attachments/assets/ef24adf0-7b4a-4f4c-8f98-36ecbe5da905" />







## Demo

Demo v6.1.0 release

[TodoCine-Angular[v6.1.0].webm](https://github.com/user-attachments/assets/14ba5b37-85af-4e68-a30a-d3b8c93e9754)





















