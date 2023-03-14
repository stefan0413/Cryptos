FROM openjdk:17-jdk-alpine

RUN mkdir /app
COPY target/Customers-0.0.1.jar /app

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "my-app.jar"]