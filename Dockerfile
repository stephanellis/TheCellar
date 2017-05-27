FROM maven:3.2-jdk-8 AS builder
COPY . /usr/src/app
WORKDIR /usr/src/app
CMD mvn clean package -DskipTests

FROM java:8-jdk-alpine
WORKDIR /
COPY --from=builder /usr/src/app/target/theCellar-0.0.1-SNAPSHOT.jar thecellar.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "thecellar.jar"]
CMD ["--spring.profiles.active=test"]
