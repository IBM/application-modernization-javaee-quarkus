#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {

  _out ------------------------------------------------------------------------------------
  
  _out Kafka
  nodeport=$(oc get svc my-cluster-kafka-external-bootstrap -n kafka --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
  if [ -z "$nodeport" ]; then
    _out Kafka is not available. Run the command: \"sh os-scripts/deploy-kafka.sh\"
  else     
    _out Kafka bootstrap server - internal URL: my-cluster-kafka-external-bootstrap.kafka:9094
  fi
  _out ------------------------------------------------------------------------------------

  _out Postgres
  nodeport=$(oc get svc postgres -n postgres --ignore-not-found --output 'jsonpath={.spec.ports[*].port}')
  if [ -z "$nodeport" ]; then
    _out Postgres is not available. Run the command: \"sh os-scripts/deploy-postgres.sh\"
  else 
    _out Postgres - internal URL: postgres.postgres:5432
  fi

}

setup
