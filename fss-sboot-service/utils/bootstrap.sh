#!/usr/bin/env bash

if test -f config/application.properties; then
  echo "File config/application.properties skipping creation."

else
  echo "File config/application.properties not found, creating it."
  sed  "s|%USERNAME%|$USER|g" src/bootstrap/application.properties.template > config/application.properties
  echo "Please, edit config/application.properties file to set your database and connection parameters."
fi


