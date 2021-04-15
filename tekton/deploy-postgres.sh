#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  _out Deploying Postgres
  
  oc project postgres > /dev/null 2>&1
  if [ $? != 0 ]; then 
      oc new-project postgres
  fi

  oc apply -f tekton/postgres/tasks
  oc apply -f tekton/postgres/pipelines
  oc apply -f tekton/postgres/pipelineruns
}

setup