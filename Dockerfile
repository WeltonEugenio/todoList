FROM ubuntu:lastest AS build


RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

COPY . .

FROM openjdk:17-jdk-slint AS runtime
RUN apt-get install maven -y
RUN mvn clean install

EXPOSE 8080


COPY --from=builder target/todolist-1.0.0.jar todolistApp.jar

ENTRYPOINT ["java","-jar","/todolistApp.jar"]