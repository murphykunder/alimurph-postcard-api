# Stage 1: Build the application
# Use an official maven image to build the Spring boot app image
FROM maven:3.8.4-openjdk-17 AS build
# Set the working directory inside the container. This is where all the following commands will be executed
WORKDIR /app
# Copy pom.xml from local directory to /app container folder
COPY pom.xml .
# Download and keep all maven dependencies so that they are available offline while building the package. The package will be build with the available dependencies downloaded only.
RUN mvn dependency:go-offline
# Copy the source code from the "src" directory in our local machine to the "/app/src" folder in the container. After this command working directory = /app/src folder.
COPY src ./src
# Build the jar file
RUN mvn clean package -DskipTests
# Extract the jar file into assets folder
# WORKDIR /assets
# RUN jar xf /app/target/alimurph-postcard-api-0.0.1-SNAPSHOT.jar



# Stage 2: Run the application
# Use an official OpenJDK image to run the application
FROM openjdk:17-jdk-slim
# Set the working directory to /app
WORKDIR /app
# COPY the jar file produced in the build stage in the working directory and rename it to alimurph-postcard-api-0.0.1.jar
COPY --from=build /app/target/alimurph-postcard-api-0.0.1-SNAPSHOT.jar ./alimurph-postcard-api-0.0.1.jar
# Copy the class fonts images extracted from the jar in the build step, into the app folder
COPY --from=build /app/target/classes/fonts /app/assets/fonts
COPY --from=build /app/target/classes/images /app/assets/images
# Setup ENV variables
ENV ALIMURPH_POSTCARD_ASSETS_PATH='/app/assets'
# Export port 8088
EXPOSE 8088
# Specify the command to run the application
CMD ["java", "-jar", "alimurph-postcard-api-0.0.1.jar"]