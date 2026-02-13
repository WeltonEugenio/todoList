# Todolist - Gerenciador de Tarefas

Este projeto é uma aplicação backend em Java que implementa um gerenciador de tarefas (To‑Do List) usando Spring Boot.

**Objetivo:** servir como referência para iniciantes em Spring Boot, mostrando como usar Spring Data JPA, Spring Security, Lombok e H2 para desenvolvimento local.

---

**Tecnologias utilizadas**

- Java 17
- Spring Boot 4.0.2
  - spring-boot-starter-web
  - spring-boot-starter-data-jpa
  - spring-boot-starter-security
  - spring-boot-starter-actuator
  - spring-boot-devtools
- Hibernate (via Spring Data JPA)
- H2 Database 2.4.240 (banco em memória para desenvolvimento)
- Lombok (redução de boilerplate)
- BCrypt (at.favre.lib:bcrypt) para hashing de senhas
- JUnit 5 + Mockito para testes
- Maven (build tool)

Arquivo `pom.xml` no projeto lista as dependências completas.

---

**Requisitos (pré-requisitos)**

- JDK 17 instalado e `JAVA_HOME` apontando para ele
- Maven 3.8+ (ou usar o wrapper `mvnw` presente no projeto)
- IDE (recomendado: VS Code ou IntelliJ IDEA)

**Extensões recomendadas (VS Code)**

- Java Extension Pack (Microsoft)
- Language Support for Java(TM) by Red Hat
- Debugger for Java
- Maven for Java
- Spring Boot Extension Pack (vscode-spring-boot)
- Lombok Annotations Support for VS Code (ou Lombok plugin no IntelliJ)
- GitLens (opcional, para git)

Instale também o plugin/agent do Lombok conforme a sua IDE para evitar warnings.

---

**Como executar localmente**

1. Buildar o projeto (com testes):

```bash
mvn clean install
```

2. Executar a aplicação usando Maven (modo dev):

```bash
mvn spring-boot:run
```

3. Ou executar o jar gerado:

```bash
java -jar target/todolist-0.0.1-SNAPSHOT.jar
```

A aplicação por padrão usa o profile `dev` (ver `application.properties`).

---

**H2 Console (banco de desenvolvimento)**

- O projeto inicia um console web do H2 (quando ativo em `dev`) em: http://localhost:8082
- Usuário/URL padrão do datasource está em `src/main/resources/application.properties`.

> Observação: Se o porto 8082 estiver em uso, mate o processo que o utiliza ou altere a porta em `H2WebServerConfig.java`.

---

**Testes**

- Para rodar apenas os testes:

```bash
mvn test
```

- Para ignorar testes no build:

```bash
mvn clean install -DskipTests
```

O projeto inclui testes básicos de contexto (classe `TodolistApplicationTests`).

---

**Configurações úteis**

- Profiles: use `-Dspring.profiles.active=dev` ou configure `spring.profiles.active` no `application.properties` para alternar entre `dev` e `prod`.
- Senhas: o projeto usa BCrypt para hashing — nunca armazene senhas em texto claro.

---

**Dicas para iniciantes**

- Ative o plugin Lombok para sua IDE para evitar erros e aproveitar anotações como `@Data`.
- Use `spring-boot-devtools` para reload automático durante desenvolvimento.
- Use o H2 apenas em dev; para produção prefira Postgres ou MySQL.
- Aprenda a usar `Spring Data JPA` e repositórios — eles reduzem muito o boilerplate de acesso a dados.

---

**Próximos passos sugeridos**

- Adicionar autenticação JWT (stateless)
- Escrever testes de integração (MockMvc / TestRestTemplate)
- Dockerizar a aplicação e o banco
- Configurar CI/CD (GitHub Actions)

---

Se quiser, eu posso também gerar uma versão em `README_pt-BR.md` (mais detalhada) ou adicionar instruções de Docker e deploy passo a passo.
