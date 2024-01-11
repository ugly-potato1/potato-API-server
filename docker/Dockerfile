# 기본 이미지 설정
FROM openjdk:17-jdk-slim

### Dockerfile 내에서 사용할 변수 JAR_FILE을 정의한다.
ARG JAR_FILE=build/libs/*.jar
### JAR_FILE 경로에 해당하는 파일을 Docker 이미지 내부로 복사한다.
COPY ${JAR_FILE} potato-dev.jar

# 컨테이너 실행 명령 설정
ENTRYPOINT ["java", "-jar", "/potato-dev.jar"]


