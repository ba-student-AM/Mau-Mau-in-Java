# Mau-Mau-in-Java

## Use the right Java version

jre or jdk 17.0.3+7 recommended

* [Adoptium.net](https://adoptium.net/de/temurin/releases/)

## How to build the jar with Maven

* mvn compile
* mvn clean package

Two Jars will be created in the /target directory. The first one includes all JavaFX dependencies. The second one with the name original-* does not include any dependencies (JavaFX is not included by default in the Java builds anymore).

## run the Jar

javaw -jar $PROJECT_PATH/target/java-maumau-1.0-SNAPSHOT.jar
