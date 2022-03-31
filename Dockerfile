FROM java:8-jdk-alpine
COPY ./target/SkilltrackerEngineerProfile-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8082
ENTRYPOINT ["java","-jar","SkilltrackerEngineerProfile-0.0.1-SNAPSHOT.jar"]
