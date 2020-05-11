#!/bin/sh

mvn clean

mvn package &&
java -jar ./target/app.jar -url lal.by
