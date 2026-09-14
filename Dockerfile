# statge 1 : build the application
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /build

COPY pom.xml .

RUN mvn dependency:go-offline -B

# copy the source code 
COPY src ./src

RUN mvn clean package -DskipTests

# statge 2 : run the application 
FROM eclipse-temurin:17-jre

WORKDIR /app

# create a non-root user to run the application 
RUN useradd --system --create-home spring

COPY --from=build /build/target/*.jar /app/app.jar

RUN chown spring:spring /app/app.jar

# switch to the non-root user 
USER spring

EXPOSE 2330

ENTRYPOINT ["java", "-jar", "/app/app.jar"]