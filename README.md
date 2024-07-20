# Todo Cine

## What is Todo Cine

Todo Cine is a web app to manage movies from [TMDB](https://www.themoviedb.org/) 

## Code

Todo Cine is an API REST developed with [Java and Springboot in backend](https://github.com/abeltran10/todocine_backend) and [Javascript's react framework in frontend](https://github.com/abeltran10/todo_cine_frontend). It's SQL database. I had to add hibernate-community-dialects in pom because my SQL server is no longer supported.


application.properties loads properties from three files, one per environment (prod, dev, test) and Constants file is missing in order you can create it for your need

## Releases
- [v3.6.0](https://github.com/abeltran10/todocine_backend/releases/tag/v3.6.0)
- [RC-3.5.1](https://github.com/abeltran10/todocine_backend/releases/tag/RC-3.5.1)
- [RC-3.5.0](https://github.com/abeltran10/todocine_backend/releases/tag/RC-3.5.0)
- [v3.0.0](https://github.com/abeltran10/todocine_backend/releases/tag/v3.0.0)
- [RC-3.0.0](https://github.com/abeltran10/todocine_backend/releases/tag/RC-3.0.0)
- [RC-2.1.0](https://github.com/abeltran10/todocine_backend/releases/tag/RC-2.1.0)
- [RC-2.0.0](https://github.com/abeltran10/todocine_backend/releases/tag/RC-2.0.0)
- [v2.0](https://github.com/abeltran10/todocine_backend/releases/tag/v2.0)
- [v1.0](https://github.com/abeltran10/todocine_backend/releases/tag/v1.0)

## Install

- Download [last release](https://github.com/abeltran10/todocine_backend/releases/tag/v3.6.0) compressed file 
- Add application.properties and Constants.java files to project
- Execute [mvn clean install] command and deploy .jar file generated in one server. [Heroku](https://heroku.com) is a free choice

## Notes
App has been migrated to SQL database. NoSQL database was for learning purpose but no I use SQL because it makes more sense.

## Demo

Demo v3.6.0 stable release
[v3.6.0.webm](https://github.com/user-attachments/assets/68cac8b8-10d3-4246-9f96-5ba73bc47f31)
















