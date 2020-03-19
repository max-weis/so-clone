FROM openjdk:8-jre-alpine

# copy jar
COPY target/qa-runner.jar .
EXPOSE 8080
# run jar
ENTRYPOINT [ "java", "-jar", "qa-runner.jar"]
