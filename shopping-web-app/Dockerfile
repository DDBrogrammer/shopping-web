# Use an official OpenJDK runtime as a parent image
FROM amazoncorretto:17-alpine-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR (assuming it's named "your-spring-boot-app.jar") to the container
COPY target/shopping-web-app-be-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that your Spring Boot application will run on
#EXPOSE 8080

# Environment variables for MySQL connection
#ENV MYSQL_HOST=localhost
#ENV MYSQL_PORT=3306
#ENV MYSQL_DATABASE=shopping_web_db
#ENV MYSQL_USERNAME=root
#ENV MYSQL_PASSWORD=daidp2403

# Copy the MySQL Connector/J JAR to the container
#COPY path/to/mysql-connector-java-8.0.26.jar /app/mysql-connector-java.jar

# Command to run your application
CMD ["java", "-jar", "app.jar"]