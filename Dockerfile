# 1단계: 빌드
FROM maven:3.9.9-eclipse-temurin-24-alpine AS build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

# 2단계: 실행
FROM eclipse-temurin:24-jdk
WORKDIR /app
COPY --from=build /build/target/neoorder-lite-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
