# Use an OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the target directory into the container
COPY target/SpringMVCProject-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 8082

# Run the application
CMD ["java", "-jar", "app.jar"]
