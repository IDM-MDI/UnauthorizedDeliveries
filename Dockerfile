FROM openjdk:17-jdk-alpine
EXPOSE 8080
ENV PROFILE = prod
ENV APPLICATION_PATH = /app
COPY build/libs/UnauthorizedDeliveries-1.0.0.jar UnauthorizedDeliveries-1.0.0.jar
COPY src/main/resources/csv ${APPLICATION_PATH}/src/main/resources/csv

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}","-jar","UnauthorizedDeliveries-1.0.0.jar"]