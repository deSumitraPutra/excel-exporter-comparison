# Use OpenJDK base image
FROM eclipse-temurin:17

# Set the working directory
WORKDIR /app

# Copy the built jar file (adjust name if needed)
COPY target/jxls-exporter-0.1.0.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
