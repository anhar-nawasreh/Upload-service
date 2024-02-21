FROM bellsoft/liberica-runtime-container:jre-17-stream-musl

WORKDIR /app

COPY target/upload-video-webapp-0.0.1-SNAPSHOT.jar /app/upload-video-webapp-0.0.1-SNAPSHOT.jar

EXPOSE 8081

CMD ["java", "-jar", "upload-video-webapp-0.0.1-SNAPSHOT.jar"]
