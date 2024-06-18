FROM maven:3.6.3

WORKDIR /server

COPY pom.xml ./
# install simple http server for serving static content
RUN mvn install

# install project dependencies
RUN mvn spring-boot:run
EXPOSE 8080
