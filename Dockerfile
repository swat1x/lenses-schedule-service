# ---------- STAGE 1: build ----------
FROM gradle:8.7-jdk21 AS builder
WORKDIR /app

# Копируем wrapper и build-файлы
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Делаем gradlew исполняемым
RUN chmod +x gradlew

# Кешируем зависимости
RUN ./gradlew --no-daemon dependencies || true

# Копируем исходники
COPY src ./src

# Сборка
RUN ./gradlew clean bootJar -x test

# ---------- STAGE 2: runtime ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Переменные окружения по умолчанию
ENV SPRING_PROFILES_ACTIVE "prod"
ENV SERVER_PORT 8080
ENV JAVA_OPTS ""

# Копируем jar
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -Dserver.port=$SERVER_PORT -jar app.jar"]
