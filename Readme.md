[![CI Build](https://github.com/v6hq/plantuml-server-v2/actions/workflows/ci.yml/badge.svg)](https://github.com/v6hq/plantuml-server-v2/actions/workflows/ci.yml)

# PlantUML Server v2 

A plantuml server with ~~spring~~ a no magic server backend. It contains also a slightly improved frontend with some vanilla js.

## Build

``mvn clean package`` should do the job.

## Running

#### Running from source

Run ```mvn compile exec:java```, open [http://localhost:8080](http://localhost:8080) and smile (a little bit).

Note: With ```amvn test exec:java --watch``` you could run tests and execute app after each save. Requires [amvn](https://github.com/nikku/amvn) to be available.

#### Running from build result

Go to your ``target`` folder and execute ``java  --add-exports=java.desktop/com.sun.imageio.plugins.png=ALL-UNNAMED -jar plantuml-server-v2-1-SNAPSHOT.jar`` but expect your version to vary. Afterwards head over to [http://localhost:8080](http://localhost:8080) and give it a try.

### Running image (from dockerhub)

With ``docker run -d --name plantuml -p 8080:8080 v6hq/plantuml-server-v2`` you should get a running container. Afterwards head over to [http://localhost:8080](http://localhost:8080) and give it a try.
