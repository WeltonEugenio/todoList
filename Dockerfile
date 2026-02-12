# -------- STAGE 1 - BUILD --------
# Usa imagem oficial do Maven com Java 17 para compilar o projeto
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia apenas o pom.xml primeiro
# Isso permite aproveitar cache do Docker se as dependências não mudarem
COPY pom.xml .

# Baixa todas as dependências do Maven antecipadamente
RUN mvn dependency:go-offline

# Copia o restante do código fonte
COPY src ./src

# Compila o projeto e gera o arquivo .jar
# -DskipTests pula os testes para build mais rápido
RUN mvn clean package -DskipTests


# -------- STAGE 2 - RUNTIME --------
# Usa imagem leve apenas com Java 17 para executar a aplicação
FROM eclipse-temurin:17-jdk-jammy

# Define diretório de trabalho no container final
WORKDIR /app

# Copia o .jar gerado no stage de build
# *.jar evita erro caso o nome tenha versão diferente
COPY --from=builder /app/target/*.jar app.jar

# Informa que a aplicação usa a porta 8080
EXPOSE 8080

# Comando que será executado quando o container iniciar
ENTRYPOINT ["java","-jar","app.jar"]
