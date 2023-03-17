FROM openjdk:17-jdk-alpine
EXPOSE 8080
ENV PROFILE = prod
COPY build/libs/UnauthorizedDeliveries-1.0.0.jar UnauthorizedDeliveries-1.0.0.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}","-jar","UnauthorizedDeliveries-1.0.0.jar"]