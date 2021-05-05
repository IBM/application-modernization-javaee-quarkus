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
  echo "Run sh scripts-docker/build-quarkus-and-run-catalog.sh once first"
  echo "Open http://localhost/CustomerOrderServicesWeb"
  
  cd ${root_folder}
  sh scripts-docker/stop-services.sh
  
  if [ -z "$POSTGRES_URL" ]; then
    echo ERROR: The enviornment variable POSTGRES_URL has not been set
    echo In this shell invoke the command \'export POSTGRES_URL=ibm-postgres-url\'
  else
    if [ -z "$POSTGRES_USER" ]; then
      echo ERROR: The enviornment variable POSTGRES_USER has not been set
      echo In this shell invoke the command \'export POSTGRES_USER=your-user\'
    else
    if [ -z "$POSTGRES_PASSWORD" ]; then
        echo ERROR: The enviornment variable POSTGRES_PASSWORD has not been set
        echo In this shell invoke the command \'export POSTGRES_PASSWORD=your-password\'
      else
        cd ${root_folder}/service-catalog-quarkus-synch/src/main/resources    
        rm application.properties.container-managed
        rm application.properties
        sed "s|KAFKA_BOOTSTRAP_SERVERS|kafka:9092|g" application.properties.template > application.properties
        mv application.properties application.properties.temp
        sed "s|POSTGRES_URL|$POSTGRES_URL|g" application.properties.temp > application.properties
        mv application.properties application.properties.temp
        sed "s/POSTGRES_USER/$POSTGRES_USER/g" application.properties.temp > application.properties
        mv application.properties application.properties.temp
        sed "s/POSTGRES_PASSWORD/$POSTGRES_PASSWORD/g" application.properties.temp > application.properties
        cp application.properties application.properties.container-managed
        rm application.properties.temp

        cd ${root_folder}/service-catalog-quarkus-synch
        mvn package
        docker build -f Dockerfile.single -t storefront-catalog .

        cd ${root_folder}/service-catalog-quarkus-synch/src/main/resources    
        rm application.properties
        cp application.properties.container-unmanaged application.properties

        cd ${root_folder}/scripts-docker
        docker-compose -f docker-compose-all-quarkus.yml up
      fi
    fi
  fi
}

setup