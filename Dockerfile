# --- Build Stage ---
# Use a base image with Gradle and JDK 21 to build the application
FROM gradle:8.5-jdk21-jammy AS build

# Set the working directory
WORKDIR /home/gradle/src

# Copy the entire project
COPY . .

# Grant execute permission for gradlew and build the application
# Skip tests for faster image builds in the CD pipeline
RUN chmod +x ./gradlew && ./gradlew build -x test

# --- Final Stage ---
# Use a minimal JRE image for the final application image
FROM eclipse-temurin:21-jre-jammy

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Set the entrypoint to run the application
# The DB credentials will be passed as environment variables in the `docker run` command
ENTRYPOINT ["java", "-jar", "app.jar"]
