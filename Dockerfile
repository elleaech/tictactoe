FROM openjdk:latest

WORKDIR /root
COPY src/. /root

RUN javac Main.java
CMD java Main