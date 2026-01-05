FROM eclipse-temurin:17-jdk-alpine
LABEL authors="jayesh"

WORKDIR /app

# FIX: Copy directly to the current directory (.) which is /app
COPY target/resource-jar.jar resource-jar.jar

# This now works because the file is exactly where Java expects it
ENTRYPOINT ["java", "-jar", "resource-jar.jar"]