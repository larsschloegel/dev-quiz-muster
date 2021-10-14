FROM openjdk:17

ENV ENVIRONMENT=prod

MAINTAINER Lars Schloegel <lars.schloegel@gmail.com>

ADD backend/target/devquizmuster.jar devquizmuster.jar

CMD [ "sh", "-c", "java -Dserver.port=$PORT -Dspring.data.mongodb.uri=$MONGO_DB_URI -jar /devquizmuster.jar" ]