#Use and official Maven image to build the Springboot api
FROM maven:3.8.4-openjdk-8 AS build

#/Set the working directiory
WORKDIR /app

#Copy the pom.xml and install dependencies
COPY pom.xml .

#Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

#Use an official OpenJDK image to run the application
FROM openjdk:8-jdk-slim

#Set the working directory
WORKDIR /app

#Copy the built JAR file from the build stage
COPY --from=build /app/target/aaic-backendapi-0.0.2-SNAPSHOT.jar .

#Expose port 8080
EXPOSE 8080

#Specifiy the commant to run the application
ENTRYPOINT ["java", "-jar", "/app/aaic-backendapi-0.0.2-SNAPSHOT.jar"]



