FROM openjdk:17.0.1-jdk-slim
EXPOSE 8080
ARG JAR_FILE=target/springboot-aws-mysql-rds-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} springboot-aws-sqs-sns-s3.jar
ENTRYPOINT ["java","-jar","/springboot-aws-sqs-sns-s3.jar"]