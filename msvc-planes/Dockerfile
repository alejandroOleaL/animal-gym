FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /app/msvc-planes

COPY ./pom.xml /app
COPY ./msvc-planes/.mvn ./.mvn
COPY ./msvc-planes/mvnw .
COPY ./msvc-planes/pom.xml .

#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
RUN ./mvnw dependency:go-offline
COPY ./msvc-planes/src ./src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
RUN mkdir ./logs

COPY --from=builder /app/msvc-planes/target/msvc-planes-0.0.1-SNAPSHOT.jar .
EXPOSE 8001

CMD ["java", "-jar", "msvc-planes-0.0.1-SNAPSHOT.jar"]