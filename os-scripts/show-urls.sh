#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {

  crcip=$(crc ip)
  _out ------------------------------------------------------------------------------------
  
  _out Kafka Strimzi operator
  nodeport=$(oc get svc my-cluster-kafka-external-bootstrap -n kafka --ignore-not-found --output 'jsonpath={.spec.ports[*].nodePort}')
  if [ -z "$nodeport" ]; then
    _out Kafka is not available. Run the command: \"sh scripts/deploy-kafka.sh\"
  else 
    _out Kafka bootstrap server - external URL: ${crcip}:${nodeport}
    _out Kafka bootstrap server - internal URL: my-cluster-kafka-external-bootstrap.kafka:9094
  fi
  _out ------------------------------------------------------------------------------------

}

setup
