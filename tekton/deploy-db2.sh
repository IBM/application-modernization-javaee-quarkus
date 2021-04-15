#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying Db2
  
  oc apply -f tekton/pipelineruns-infra/db2.yaml 
}

setup