# Todo Cine

## What is Todo Cine

Todo Cine is a web app to manage movies from [TMDB](https://www.themoviedb.org/) 

## Code

Todo Cine is an API REST developed with [Java and Springboot in backend](https://github.com/abeltran10/todocine_backend) and there are two frontends one with [Javascript's react framework](https://github.com/abeltran10/todo_cine_frontend) and another with [Angular](https://github.com/abeltran10/todocine_front_angular). It's SQL database. I had to add hibernate-community-dialects in pom because my SQL server is no longer supported.

application.properties loads properties from three files, one per environment (prod, dev, test) and Constants file is missing in order you can create it for your need.


## Last release
- [v6.0.3](https://github.com/abeltran10/todocine_backend/releases/tag/v6.0.3)

## Install

- Download [last release](https://github.com/abeltran10/todocine_backend/releases/tag/v6.0.3) compressed file. 
- Add application.properties and Constants.java files to project.
- Execute [mvn clean install] command and deploy .jar file generated in one server.

## Notes
App has been migrated to SQL database. NoSQL database was for learning purpose but now I use SQL because it makes more sense.

## Demo

Demo v6.0.3 release


[TodoCine-Angular[v6.0.3].webm](https://github.com/user-attachments/assets/1abf2125-859e-49ce-bb11-f25f95a9d12a)




















