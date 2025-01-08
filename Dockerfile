FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR file generated by Maven into the container
COPY SpringMVCProject-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 8082

# Run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
