#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying Kafka
  
  oc project kafka > /dev/null 2>&1
  if [ $? != 0 ]; then 
      oc new-project kafka
  fi

  oc apply -f tekton/kafka/tasks
  oc apply -f tekton/kafka/pipelines
  oc apply -f tekton/kafka/pipelineruns
}

setup