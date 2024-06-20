FROM openjdk:22-jdk

WORKDIR /server

COPY target/voronoiDiagram.jar ./

CMD ["java","-jar","voronoiDiagram.jar"]

#FROM openjdk:22-jdk
#FROM jelastic/maven:3.8.6-openjdk-20.ea-b24

#FROM jelastic/maven:3.9.4-openjdk-22.ea-b17
#WORKDIR /server
#
#COPY pom.xml ./
## install simple http server for serving static content
#RUN mvn clean package
#
#COPY target ./
#
## install project dependencies
##RUN mvn spring-boot:run
#EXPOSE 8080
