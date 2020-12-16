#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

function _out() {
  echo "$(date +'%F %H:%M:%S') $@"
}

function setup() {
  cd ${root_folder}
  rm ${root_folder}/monolith-websphere-90/supporting-assets/sessions/db2/db2jcc_license_cu.jar
  rm ${root_folder}/monolith-websphere-90/supporting-assets/sessions/db2/db2jcc4.jar
  rm ${root_folder}/monolith-websphere-90/lib/db2jcc_license_cu.jar
  rm ${root_folder}/monolith-websphere-90/CustomerOrderServicesTest/WebContent/WEB-INF/lib/dbunit-2.2.jar
  rm ${root_folder}/monolith-websphere-90/CustomerOrderServicesTest/WebContent/WEB-INF/lib/junit.jar
  rm ${root_folder}/monolith-websphere-90/CustomerOrderServicesTest/WebContent/WEB-INF/lib/junitee.jar
  rm ${root_folder}/monolith-websphere-90/resources/jmx_exporter/jmx_prometheus_javaagent-0.11.0.jar
  rm ${root_folder}/monolith-websphere-90/resources/db2/db2jcc4.jar
  rm ${root_folder}/monolith-websphere-90/resources/db2/db2jcc_license_cu.jar
  rm ${root_folder}/monolith-websphere-liberty/resources/jmx_exporter/jmx_prometheus_javaagent-0.11.0.jar
  rm ${root_folder}/monolith-websphere-liberty/resources/db2/db2jcc4.jar
  rm ${root_folder}/monolith-websphere-liberty/resources/db2/db2jcc_license_cu.jar
  rm ${root_folder}/monolith-websphere-90/lib/db2jcc4.jar
   
  cd ${root_folder}
  rm -rf temp
  mkdir temp
  cd temp
  git clone https://github.com/ibm-cloud-architecture/cloudpak-for-applications.git
  cd cloudpak-for-applications
  git checkout -q was90 
  
  cp supporting-assets/sessions/db2/db2jcc_license_cu.jar ${root_folder}/monolith-websphere-90/supporting-assets/sessions/db2/db2jcc_license_cu.jar
  cp supporting-assets/sessions/db2/db2jcc4.jar ${root_folder}/monolith-websphere-90/supporting-assets/sessions/db2/db2jcc4.jar
  cp lib/db2jcc_license_cu.jar ${root_folder}/monolith-websphere-90/lib/db2jcc_license_cu.jar
  cp CustomerOrderServicesTest/WebContent/WEB-INF/lib/dbunit-2.2.jar ${root_folder}/monolith-websphere-90/CustomerOrderServicesTest/WebContent/WEB-INF/lib/dbunit-2.2.jar
  cp CustomerOrderServicesTest/WebContent/WEB-INF/lib/junit.jar ${root_folder}/monolith-websphere-90/CustomerOrderServicesTest/WebContent/WEB-INF/lib/junit.jar
  cp CustomerOrderServicesTest/WebContent/WEB-INF/lib/junitee.jar ${root_folder}/monolith-websphere-90/CustomerOrderServicesTest/WebContent/WEB-INF/lib/junitee.jar
  cp resources/jmx_exporter/jmx_prometheus_javaagent-0.11.0.jar ${root_folder}/monolith-websphere-90/resources/jmx_exporter/jmx_prometheus_javaagent-0.11.0.jar
  cp resources/db2/db2jcc4.jar ${root_folder}/monolith-websphere-90/resources/db2/db2jcc4.jar
  cp resources/db2/db2jcc_license_cu.jar ${root_folder}/monolith-websphere-90/resources/db2/db2jcc_license_cu.jar
  cp resources/jmx_exporter/jmx_prometheus_javaagent-0.11.0.jar ${root_folder}/monolith-websphere-liberty/resources/jmx_exporter/jmx_prometheus_javaagent-0.11.0.jar
  cp resources/db2/db2jcc4.jar ${root_folder}/monolith-websphere-liberty/resources/db2/db2jcc4.jar
  cp resources/db2/db2jcc_license_cu.jar ${root_folder}/monolith-websphere-liberty/resources/db2/db2jcc_license_cu.jar
  cp lib/db2jcc4.jar ${root_folder}/monolith-websphere-90/lib/db2jcc4.jar

  cd ${root_folder}
  #rm -r temp
}

setup