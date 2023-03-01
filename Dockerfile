FROM alpine
RUN apk add --no-cache graphviz openjdk17-jre fontconfig ttf-dejavu
COPY target/plantuml-server-v2-1-SNAPSHOT.jar plantuml-server-v2-1-SNAPSHOT
ENTRYPOINT ["java", "--add-exports=java.desktop/com.sun.imageio.plugins.png=ALL-UNNAMED","-Djava.awt.headless=true","-jar","/plantuml-server-v2-1-SNAPSHOT"]
