#--------------------------.
# OPENJDK-JRE-8-ALPINE     |
#--------------------------'
FROM openjdk:8-jre-alpine
COPY target/ms-order-0.0.1-SNAPSHOT.jar /order.jar
ENTRYPOINT java -jar /order.jar
