#!/usr/bin/env bash


./generate-code.sh

set -x
set -e
cd out/client
gradle -i clean jar publishToMavenLocal
