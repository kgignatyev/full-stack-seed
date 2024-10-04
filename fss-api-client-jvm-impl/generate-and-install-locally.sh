#!/usr/bin/env bash


./generate-code.sh

cd out/client
gradle -i build publishToMavenLocal
