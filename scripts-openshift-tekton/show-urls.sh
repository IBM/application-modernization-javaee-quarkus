#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

exec 3>&1

if [ -z "$1" ]
then
  sh $root_folder/scripts-openshift/show-urls.sh app-mod-dev-tekton
else
  sh $root_folder/scripts-openshift/show-urls.sh
fi


