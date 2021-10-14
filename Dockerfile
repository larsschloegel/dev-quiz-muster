FROM openjdk:17

ENV ENVIRONMENT=prod

MAINTAINER Lars Schloegel <lars.schloegel@gmail.com>

ADD backend/target/devquiz.jar app.jar

CMD [ "sh", "-c", "java -Dserver.port=$PORT -jar /devquizmuster.jar" ]