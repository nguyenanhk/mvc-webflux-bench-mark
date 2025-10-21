# ----------------------------------------------------------
# Production Dockerfile (runtime only)
# ----------------------------------------------------------
# Expect the JAR to already be built at:
# mvc-app-j21/build/libs/mvc-app-j21-1.0.0.jar
# ----------------------------------------------------------

FROM eclipse-temurin:21-alpine

WORKDIR /app

# Copy only the compiled JAR from host to container
COPY build/libs/mvc-app-j21-1.0.0.jar app.jar

EXPOSE 8082

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
