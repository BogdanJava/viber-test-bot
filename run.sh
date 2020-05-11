#!/bin/sh

./mvnw clean

./mvnw package &&
java -jar ./target/app.jar -url "$1" -token "$2"
