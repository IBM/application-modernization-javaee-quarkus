#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {

  cd ${root_folder}/scripts-docker

  docker rm -f storefront-mf-account
  docker rm -f storefront-mf-shell
  docker rm -f storefront-mf-order
  docker rm -f storefront-mf-catalog
  docker rm -f storefront-mf-messaging
  docker rm -f storefront-mf-navigator
}

setup