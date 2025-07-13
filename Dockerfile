# 1단계: Gradle을 이용한 빌드용 이미지
FROM gradle:8.4.0-jdk21 AS builder

# 작업 디렉토리 설정
WORKDIR /app

# 전체 프로젝트 복사
COPY . .

# 종속성 캐시 및 빌드
RUN gradle clean build -x test

# ------------------------------------------------------

# 2단계: 실제 애플리케이션 실행용 이미지
FROM eclipse-temurin:21-jdk

# JAR 복사 (빌드된 JAR 경로)
ARG JAR_FILE=build/libs/*.jar
COPY --from=builder /app/${JAR_FILE} app.jar

# 8080 포트 오픈
EXPOSE 8080

# 앱 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]