#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  cd ${root_folder}/monolith-liberty/Common
  cp createschema.sh createschema.sh
  docker build -f ${root_folder}/db2/Dockerfile -t db2-niklas .

}

setup
