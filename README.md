# Mau-Mau-in-Java

## How to build the jar with Maven

* mvn compile
* mvn clean package

Two Jars will be created in the /target directory. The first one includes all JavaFX dependencies. The second one with the name original-* does not include any dependencies.

## run the Jar

javaw -jar $PROJECT_PATH/target/java-maumau-1.0-SNAPSHOT.jar
