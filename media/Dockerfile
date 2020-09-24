FROM adoptopenjdk:13.0.2_8-jre-hotspot

COPY target/*.jar app.jar
EXPOSE 8080


RUN groupadd -r -g 3000 elastictranscoder && useradd --no-log-init -r -u 1500 -g elastictranscoder elastictranscoder

USER 1500:3000

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
