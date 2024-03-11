#!/usr/bin/env bash

./generate-java-code.sh

cd out/server
mvnd clean install
