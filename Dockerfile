FROM openjdk:latest

WORKDIR /root
COPY . /root

RUN javac Main.java
CMD java Main