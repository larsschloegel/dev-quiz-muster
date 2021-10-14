FROM openjdk:17

ENV ENVIRONMENT=prod

MAINTAINER Lars Schloegel <lars.schloegel@gmail.com>

ADD backend/target/devquiz.jar devquiz.jar

CMD [ "sh", "-c", "java -Dserver.port=$PORT -Dspring.data.mongodb.uri=$MONGO_DB_URI -jar /devquizmuster.jar" ]