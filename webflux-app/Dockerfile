# ----------------------------------------------------------
# Production Dockerfile (runtime only)
# ----------------------------------------------------------
# Expect the JAR to already be built at:
# webflux-app/build/libs/webflux-app-1.0.0.jar
# ----------------------------------------------------------

FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy only the compiled JAR from host to container
COPY build/libs/webflux-app-1.0.0.jar app.jar

# Expose port
EXPOSE 8081

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
