FROM eclipse-temurin:21-jre

LABEL maintainer="bhavinvirani45@gmail.com"

# Working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]

# Health check to ensure the application is running
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1



# Optional: Add a label for versioning
LABEL version="1.0"
# Optional: Add a label for description
LABEL description="Spring REST application running in a Docker container"

