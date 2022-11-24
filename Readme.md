![Build](https://github.com/kDot/plantuml-springserver/actions/workflows/maven.yml/badge.svg)

# PlantUML Springserver 

A plantuml server with Spring as server backend. Contains even a slightly improved frontend with some vanilla js.

## Build

With maven ```mvn clean package``` should do the job.

## Running

Go to ``target`` folder and execute ``java  --add-exports=java.desktop/com.sun.imageio.plugins.png=ALL-UNNAMED -jar plantuml-springserver-0.0.1-SNAPSHOT.jar`` (your version might vary..
Afterwards head over to [http://localhost:8080](http://localhost:8080) and give it a try.