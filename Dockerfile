FROM gradle:5.5.1-jdk8
COPY . /home/sp-rest
WORKDIR /home/sp-rest
RUN gradle build
CMD ["./start.sh"]