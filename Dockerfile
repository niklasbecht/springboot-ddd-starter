# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:21-jdk
# TODO exchange image

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper and build files
COPY gradlew gradlew.bat build.gradle settings.gradle ./
COPY gradle/ gradle/

# Make gradlew executable
RUN chmod +x gradlew

# Copy the source code
COPY src/ src/

# Build the application
RUN ./gradlew build --no-daemon

# Expose the port the app runs on
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "build/libs/StaffMemberService-0.0.1-SNAPSHOT.jar"]
