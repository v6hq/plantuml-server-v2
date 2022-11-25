FROM eclipse-temurin:17-jre-ubi9-minimal
COPY target/plantuml-springserver-0.0.1-SNAPSHOT.jar plantuml-springserver-0.0.1-SNAPSHOT
ENTRYPOINT ["java","--add-exports=java.desktop/com.sun.imageio.plugins.png=ALL-UNNAMED","-jar","/plantuml-springserver-0.0.1-SNAPSHOT"]