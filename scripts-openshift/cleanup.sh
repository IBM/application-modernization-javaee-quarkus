#!/bin/bash

SCRIPT_FOLDER="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
PROJECT_FOLDER="$(cd $SCRIPT_FOLDER; cd ..; pwd )"

exec 3>&1

while true; do
    read -p "Do you wish to cleanup = delete everything in this project? [y|n] " yn
    case $yn in
        [Yy]* ) break;;
        [Nn]* ) exit;;
        * ) echo "Please answer yes or no.";;
    esac
done

echo Cleanup

oc delete project app-mod-dev
oc delete project app-mod-stage
oc delete project kafka
oc delete project postgres
oc delete project db2


