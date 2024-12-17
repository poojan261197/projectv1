# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim as builder

RUN apt-get update && apt-get install -y maven

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download the dependencies to take advantage of Docker's caching mechanism
COPY pom.xml .

# Download the dependencies
RUN mvn dependency:go-offline

# Copy the entire project
COPY . .

# Package the application as a .jar file
RUN mvn clean package -DskipTests

# Create a new stage to create the runtime image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the .jar file from the builder stage
COPY --from=builder /app/target/customer-0.0.1-SNAPSHOT.jar customer-api.jar

# Expose the port that the application will run on
EXPOSE 8080

# Define the command to run the app
ENTRYPOINT ["java", "-jar", "customer-api.jar"]
