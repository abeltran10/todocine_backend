# --- Paso 1: Compilar la aplicación (Multistage Build) ---
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar el archivo de configuración de dependencias
COPY pom.xml .

# Descargar las dependencias (se queda en caché si no cambia el pom.xml)
RUN mvn dependency:go-offline -B

# Copiar el código fuente del proyecto
COPY src ./src

# Compilar el proyecto saltando los tests para acelerar el despliegue
RUN mvn clean package -Pprod -DskipTests

# --- Paso 2: Crear la imagen ligera para correr la app ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copiar solo el archivo .jar resultante del paso anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto por defecto
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]