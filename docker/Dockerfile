# 기본 이미지 설정
FROM openjdk:17-jdk-slim

# GitHub Actions에서 빌드한 JAR 파일 복사
COPY build/libs/potato-API-server-0.0.1-SNAPSHOT.jar potato-dev.jar

# 컨테이너 실행 명령 설정
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/potato-dev.jar"]
