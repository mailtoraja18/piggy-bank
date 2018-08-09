FROM openjdk:10-jdk
VOLUME /tmp
ARG JAR_FILE
COPY "target/account-service*.jar" app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=k8 -Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]