FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /opt/app
COPY ./producer/src ./producer/src
COPY ./producer/pom.xml ./producer/pom.xml
COPY ./consumer/src ./consumer/src
COPY ./consumer/pom.xml ./consumer/pom.xml
RUN mvn -B -e compile assembly:single -DskipTests -f ./producer/pom.xml
RUN mvn -B -e compile assembly:single -DskipTests -f ./consumer/pom.xml

FROM eclipse-temurin:11 AS producer-run
WORKDIR /app
COPY --from=build /opt/app/producer/target/producer-1.0-SNAPSHOT-jar-with-dependencies.jar /app/producer.jar
CMD ["java", "-jar", "producer.jar"]

FROM eclipse-temurin:11 AS consumer-run
WORKDIR /app
COPY --from=build /opt/app/consumer/target/consumer-1.0-SNAPSHOT-jar-with-dependencies.jar /app/consumer.jar
CMD ["java", "-jar", "consumer.jar"]