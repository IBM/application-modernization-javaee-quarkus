#!/bin/bash

root_folder="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Installing Kafka
  
  oc project kafka  > /dev/null 2>&1
  if [ $? != 0 ]; then 
      oc new-project kafka
  fi
    
  curl -L https://github.com/strimzi/strimzi-kafka-operator/releases/download/0.15.0/strimzi-cluster-operator-0.15.0.yaml \
    | sed 's/namespace: .*/namespace: kafka/' \
    | oc apply -f - -n kafka 

    oc apply -f ${root_folder}/scripts-openshift/kafka-cluster.yaml -n kafka 

    oc expose svc/my-cluster-kafka-external-bootstrap --port=9094
   
  _out Done installing Kafka
  _out Wait until the pods have been started
  _out Run this command \(potentially multiple times\): \"oc wait kafka/my-cluster --for=condition=Ready --timeout=300s -n kafka\"
  _out After this run \"sh scripts-openshift/show-urls.sh\" to get the Kafka broker URL
}

setup

