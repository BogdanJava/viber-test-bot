#!/bin/sh

mvn clean

mvn package &&
java -jar ./target/app.jar -url "$1" -token "$2"
