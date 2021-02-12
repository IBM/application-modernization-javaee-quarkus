#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  echo "Run sh scripts-docker/run-monolith-db2.sh first"
  echo "Run sh scripts-docker/run-database-postgres-catalog.sh first"
  echo "Run sh scripts-docker/run-kafka.sh first"
  echo curl  \"http://localhost:9088/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2\"
    
  cd ${root_folder}
  sh scripts-docker/stop-services.sh
  
  cd ${root_folder}/monolith-open-liberty-cloud-native

  cp src/main/liberty/config/server-local.xml src/main/liberty/config/server.xml

  cp src/main/resources/META-INF/microprofile-config-local.properties src/main/resources/META-INF/microprofile-config.properties 

  mvn clean package

  # unfortunately this doesn't work
  # https://openliberty.io/guides/getting-started.html#running-the-application-from-a-minimal-runnable-jar
  #mvn liberty:package -Dinclude=runnable
  #java -jar target/service.jar

  # unfortunately this doesn't work
  #mvn liberty:create liberty:install-feature liberty:package -Dinclude=runnable
  #java -jar target/service.jar

  mvn liberty:dev
}

setup
