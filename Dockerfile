FROM openjdk:17-jdk

#WORKDIR /app

# volume for temporary files
VOLUME /tmp

# Copy JAR file from the local filesystem to the Docker image
COPY target/predictions-0.0.1-SNAPSHOT.jar app/app.jar

# Download TailwindCSS binary, make it executable, and move it
#RUN curl -sLO https://github.com/tailwindlabs/tailwindcss/releases/latest/download/tailwindcss-linux-x64 \
#    && chmod +x tailwindcss-linux-x64 \
#    && mv tailwindcss-linux-x64 tailwindcss

#RUN ./tailwindcss

#RUN /usr/local/bin/tailwindcss -i input.css -o output.css --watch

#RUN ./tailwindcss -i input.css -o output.css --minify

# entry point command to run the application
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app/app.jar"]
