# Use uma imagem base do OpenJDK 17 (ou a versão que você está usando)
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho no container
WORKDIR /app

# Copia o arquivo JAR gerado para o container
COPY target/quest-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta que a aplicação vai usar
EXPOSE 8080

# Define o comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]