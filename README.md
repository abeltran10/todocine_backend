# Todo Cine

## What is Todo Cine

Todo Cine is a web app to manage movies from [TMDB](https://www.themoviedb.org/) 

## Code

Todo Cine is an API REST developed with [Java and Springboot in backend](https://github.com/abeltran10/todocine_backend) and [Javascript's react framework in frontend](https://github.com/abeltran10/todo_cine_frontend). It's SQL database. I had to add hibernate-community-dialects in pom because my SQL server is no longer supported.


application.properties loads properties from three files, one per environment (prod, dev, test) and Constants file is missing in order you can create it for your need

## Last release
- [v5.1.2](https://github.com/abeltran10/todocine_backend/releases/tag/v5.1.2)

## Install

- Download [last release](https://github.com/abeltran10/todocine_backend/releases/tag/v5.1.2) compressed file 
- Add application.properties and Constants.java files to project
- Execute [mvn clean install] command and deploy .jar file generated in one server. [Heroku](https://heroku.com) is a free choice

## Notes
App has been migrated to SQL database. NoSQL database was for learning purpose but now I use SQL because it makes more sense.

## Demo

Demo v5.1.2 stable release

[TodoCine-v5.1.2.webm](https://github.com/user-attachments/assets/fea85178-9c71-494f-b6c5-1c200853d970)




















