# --- Etapa 1: BUILD (Construcción del JAR) ---
FROM eclipse-temurin:25-jdk-alpine as builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# --- Etapa 2: RUNTIME (Ejecución del JAR) ---
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar product.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "product.jar" ]


# -------------------------------
# 📘 EXPLICACIÓN RÁPIDA
# -------------------------------

# 🏗️ BUILD
# Es como una computadora temporal que solo se usa para
# construir el .jar con Maven.

# 🚀 RUNTIME
# Es la computadora final que solo ejecuta la aplicación,
# por eso solo necesita el JRE y el .jar ya construido.

# 🛠️ RUN vs ▶️ ENTRYPOINT
# RUN: se ejecuta al construir la imagen (ej: compilar el proyecto).
# ENTRYPOINT: se ejecuta cuando el contenedor inicia (ej: correr la app).

# 🌐 EXPOSE
# Documenta que la app escucha en el puerto 8080.