FROM debian:bullseye

# Install OpenJDK and Node.js
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk nodejs npm && \
    npm install

# Set working directory
WORKDIR /app

# Copy your Java application JAR file
COPY target/predictions-0.0.1-SNAPSHOT.jar app/app.jar

# Set the entry point command to run the Java application
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app/app.jar"]

