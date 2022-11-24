![Build](https://github.com/kDot/plantuml-springserver/actions/workflows/maven.yml/badge.svg)

# PlantUML Springserver 

A plantuml server with Spring as server backend. It contains also a slightly improved frontend with some vanilla js.

## Build

``mvn clean package`` should do the job.

## Running

Go to your ``target`` folder and execute ``java  --add-exports=java.desktop/com.sun.imageio.plugins.png=ALL-UNNAMED -jar plantuml-springserver-0.0.1-SNAPSHOT.jar`` but expect your version to vary. Afterwards head over to [http://localhost:8080](http://localhost:8080) and give it a try.