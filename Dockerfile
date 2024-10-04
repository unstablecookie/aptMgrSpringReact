FROM amazoncorretto:21
COPY target/*.jar aptmgr.jar
COPY target/classes/static target/classes/static
ENTRYPOINT ["java","-jar","/aptmgr.jar"]