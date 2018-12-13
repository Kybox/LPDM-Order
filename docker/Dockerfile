#--------------------------.
# GIT                      |
#--------------------------'
FROM alpine/git as clone
# Set the working directory
WORKDIR /app
# Add Json lastest SHA commit to check update for the clone cmd
ADD https://api.github.com/repos/vyjorg/LPDM-Order/git/refs/heads/master /app
# Clone the Github repository
RUN git clone -b master https://github.com/vyjorg/LPDM-Order.git

#--------------------------.
# MAVEN                    |
#--------------------------'
FROM maven:3.6-jdk-8-alpine as builder
# Set the working directory
WORKDIR /app
# Copy data from the cloned repository to the working directory
COPY --from=clone /app/LPDM-Order /app
# Build the project
RUN mvn clean package -Dmaven.test.skip=true

#--------------------------.
# OPENJDK-JRE-8-ALPINE     |
#--------------------------'
#FROM openjdk:8-jre-alpine
#WORKDIR /app
#COPY --from=builder /app /app
CMD ["java", "-jar", "target/ms-order-0.0.1-SNAPSHOT.jar"]
