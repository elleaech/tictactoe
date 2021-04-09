FROM openjdk:latest

WORKDIR /root
COPY . /root

RUN javac src/Main.java
CMD java src/Main