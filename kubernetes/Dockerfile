FROM gradle:6.7.0-jdk11 as builder

RUN pwd
COPY . /workspace
RUN apt-get -y update
RUN apt-get -y install make

WORKDIR /workspace

RUN make clean all
RUN mv /workspace/build/libs/recipe-scraper-*.jar /recipe-scraper.jar

RUN pwd

WORKDIR /
RUN rm -rf /workspace

FROM openjdk:11.0-jre-slim

COPY --from=builder /recipe-scraper.jar /

RUN ls -alh

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "recipe-scraper.jar"]
