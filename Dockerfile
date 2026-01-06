# STAGE 1: Build the application
# We use a Maven image to compile your code inside Docker
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
# This command creates the 'target' folder and the .jar file inside the container
RUN mvn clean package -DskipTests

# STAGE 2: Run the application
# Now we copy the jar we just built in Stage 1 to the final image
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the jar from the 'build' stage to the current spot
# Note: Ensure the name here matches the <finalName> in your pom.xml
COPY --from=build /app/target/resource-jar.jar app.jar

# Run the app with your memory optimizations
ENTRYPOINT ["java", "-Xmx256m", "-Xss512k", "-XX:+UseSerialGC", "-jar", "app.jar"]