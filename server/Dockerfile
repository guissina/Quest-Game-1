# Estágio 1: Build da aplicação
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copia o pom.xml primeiro para otimizar cache do Docker
COPY ./pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código fonte
COPY ./src ./src

# Compila a aplicação
RUN mvn clean package -DskipTests

# Estágio 2: Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia o JAR do estágio de build
COPY --from=build /app/target/quest-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]