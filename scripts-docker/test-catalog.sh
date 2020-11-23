#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {

  curl http://localhost:8080/CustomerOrderServicesWeb/jaxrs/Category

  
}

setup