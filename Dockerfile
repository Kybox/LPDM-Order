#--------------------------.
# OPENJDK-JRE-8-ALPINE     |
#--------------------------'
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY target/ms-order-0.0.1-SNAPSHOT.jar /app
CMD ["java", "-jar", "app/ms-order-0.0.1-SNAPSHOT.jar"]
