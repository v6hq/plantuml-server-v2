[![CI Build](https://github.com/v6hq/plantuml-server-v2/actions/workflows/maven.yml/badge.svg)](https://github.com/v6hq/plantuml-server-v2/actions/workflows/maven.yml)

# PlantUML Server v2 

A plantuml server with Spring as server backend. It contains also a slightly improved frontend with some vanilla js.

## Build

``mvn clean package`` should do the job.

## Running (from source)

Go to your ``target`` folder and execute ``java  --add-exports=java.desktop/com.sun.imageio.plugins.png=ALL-UNNAMED -jar plantuml-server-v2-1-SNAPSHOT.jar`` but expect your version to vary. Afterwards head over to [http://localhost:8080](http://localhost:8080) and give it a try.

## Running (from image)

With ``docker run -d --name plantuml -p 8080:8080 v6hq/plantuml-server-v2`` you should get a running container. Afterwards head over to [http://localhost:8080](http://localhost:8080) and give it a try.