#--------------------------.
# OPENJDK-JRE-8-ALPINE     |
#--------------------------'
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY target/ms-order-0.0.1-SNAPSHOT.jar /app/order.jar
CMD ["java", "-jar", "app/order.jar"]
