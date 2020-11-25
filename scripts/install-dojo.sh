#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  cd ${root_folder}/monolith-open-liberty/CustomerOrderServicesWeb/WebContent
  rm -R ./dojo
  rm -R ./dojo_built
  cd ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent
  rm -R ./dojo
  rm -R ./dojo_built
  
  cd ${root_folder}
  mkdir temp
  cd temp
  git clone https://github.com/ibm-cloud-architecture/cloudpak-for-applications.git
  cd cloudpak-for-applications
  git checkout -q liberty 
  
  cp -r CustomerOrderServicesWeb/WebContent/dojo ${root_folder}/monolith-open-liberty/CustomerOrderServicesWeb/WebContent/dojo
  cp -r CustomerOrderServicesWeb/WebContent/dojo_built ${root_folder}/monolith-open-liberty/CustomerOrderServicesWeb/WebContent/dojo_built

  cp -r CustomerOrderServicesWeb/WebContent/dojo ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo
  cp -r CustomerOrderServicesWeb/WebContent/dojo_built ${root_folder}/frontend-dojo/CustomerOrderServicesWeb/WebContent/dojo_built

  cd ${root_folder}
  rm -r temp
}

setup