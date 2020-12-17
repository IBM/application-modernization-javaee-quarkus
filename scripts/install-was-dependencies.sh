#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  file="${root_folder}/monolith-websphere-90/supporting-assets/sessions/db2/db2jcc4.jar"
  if [ -f "$file" ]
  then
	  echo "Dependencies already downloaded"
  else
	  cd ${root_folder}
    rm -f ${root_folder}/monolith-websphere-90/supporting-assets/sessions/db2/db2jcc_license_cu.jar
    rm -f ${root_folder}/monolith-websphere-90/supporting-assets/sessions/db2/db2jcc4.jar
    rm -f ${root_folder}/monolith-websphere-90/lib/db2jcc_license_cu.jar
    rm -f ${root_folder}/monolith-websphere-90/CustomerOrderServicesTest/WebContent/WEB-INF/lib/dbunit-2.2.jar
    rm -f ${root_folder}/monolith-websphere-90/CustomerOrderServicesTest/WebContent/WEB-INF/lib/junit.jar
    rm -f ${root_folder}/monolith-websphere-90/CustomerOrderServicesTest/WebContent/WEB-INF/lib/junitee.jar
    rm -f ${root_folder}/monolith-websphere-90/resources/jmx_exporter/jmx_prometheus_javaagent-0.11.0.jar
    rm -f ${root_folder}/monolith-websphere-90/resources/db2/db2jcc4.jar
    rm -f ${root_folder}/monolith-websphere-90/resources/db2/db2jcc_license_cu.jar
    rm -f ${root_folder}/monolith-websphere-liberty/resources/jmx_exporter/jmx_prometheus_javaagent-0.11.0.jar
    rm -f ${root_folder}/monolith-websphere-liberty/resources/db2/db2jcc4.jar
    rm -f ${root_folder}/monolith-websphere-liberty/resources/db2/db2jcc_license_cu.jar
    rm -f ${root_folder}/monolith-websphere-90/lib/db2jcc4.jar
    
    cd ${root_folder}
    rm -rf temp
    mkdir temp
    cd temp
    git clone https://github.com/ibm-cloud-architecture/cloudpak-for-applications.git
    cd cloudpak-for-applications
    git checkout -q was90 
    rm -rf .git
    
    mkdir -p ${root_folder}/monolith-websphere-90/supporting-assets/sessions/db2
    \cp -rf supporting-assets/sessions/db2/db2jcc_license_cu.jar ${root_folder}/monolith-websphere-90/supporting-assets/sessions/db2/db2jcc_license_cu.jar
    \cp -rf supporting-assets/sessions/db2/db2jcc4.jar ${root_folder}/monolith-websphere-90/supporting-assets/sessions/db2/db2jcc4.jar
    
    mkdir -p ${root_folder}/monolith-websphere-90/CustomerOrderServicesTest/WebContent/WEB-INF/lib/
    \cp -rf CustomerOrderServicesTest/WebContent/WEB-INF/lib/dbunit-2.2.jar ${root_folder}/monolith-websphere-90/CustomerOrderServicesTest/WebContent/WEB-INF/lib/dbunit-2.2.jar
    \cp -rf CustomerOrderServicesTest/WebContent/WEB-INF/lib/junit.jar ${root_folder}/monolith-websphere-90/CustomerOrderServicesTest/WebContent/WEB-INF/lib/junit.jar
    \cp -rf CustomerOrderServicesTest/WebContent/WEB-INF/lib/junitee.jar ${root_folder}/monolith-websphere-90/CustomerOrderServicesTest/WebContent/WEB-INF/lib/junitee.jar
    
    \cp -rf lib/db2jcc_license_cu.jar ${root_folder}/monolith-websphere-90/lib/db2jcc_license_cu.jar
    \cp -rf lib/db2jcc4.jar ${root_folder}/monolith-websphere-90/lib/db2jcc4.jar
    \cp -rf resources/jmx_exporter/jmx_prometheus_javaagent-0.11.0.jar ${root_folder}/monolith-websphere-90/resources/jmx_exporter/jmx_prometheus_javaagent-0.11.0.jar
    
    mkdir -p ${root_folder}/monolith-websphere-90/resources/db2
    \cp -rf resources/db2/db2jcc4.jar ${root_folder}/monolith-websphere-90/resources/db2/db2jcc4.jar
    \cp -rf resources/db2/db2jcc_license_cu.jar ${root_folder}/monolith-websphere-90/resources/db2/db2jcc_license_cu.jar
    
    \cp -rf resources/jmx_exporter/jmx_prometheus_javaagent-0.11.0.jar ${root_folder}/monolith-websphere-liberty/resources/jmx_exporter/jmx_prometheus_javaagent-0.11.0.jar
    
    mkdir -p ${root_folder}/monolith-websphere-liberty/resources/db2
    \cp -rf resources/db2/db2jcc4.jar ${root_folder}/monolith-websphere-liberty/resources/db2/db2jcc4.jar
    \cp -rf resources/db2/db2jcc_license_cu.jar ${root_folder}/monolith-websphere-liberty/resources/db2/db2jcc_license_cu.jar

    cd ${root_folder}
    rm -r temp
  fi
}

setup