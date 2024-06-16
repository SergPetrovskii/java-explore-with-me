FROM amazoncorretto:11
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8081
COPY target/*.jar ewm-main-service.jar
LABEL authors="Petrovskii"
ENTRYPOINT ["java","-jar","/ewm-main-service.jar"]
