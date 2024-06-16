FROM amazoncorretto:11
COPY client/target/*.jar stat_client.jar
COPY dto/target/*.jar stat_dto.jar
COPY service/target/*.jar stat_service.jar
LABEL authors="Petrovskii"
ENTRYPOINT ["java","-jar","/stat_client.jar"]
ENTRYPOINT ["java","-jar","/stat_dto.jar"]
ENTRYPOINT ["java","-jar","/stat_service.jar"]
