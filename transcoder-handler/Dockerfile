FROM adoptopenjdk:13.0.2_8-jre-hotspot

RUN apt update
RUN apt install -y software-properties-common
RUN add-apt-repository -y ppa:jonathonf/ffmpeg-4
RUN apt install -y ffmpeg
RUN ffmpeg -version

COPY target/*.jar app.jar
EXPOSE 8080

RUN groupadd -r -g 3000 elastictranscoder && useradd --no-log-init -r -u 1500 -g elastictranscoder elastictranscoder

USER 1500:3000

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
