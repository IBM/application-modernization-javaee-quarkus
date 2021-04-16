#!/bin/bash

root_folder=$(cd $(dirname $0); cd ..; pwd)

oc delete -f $root_folder/debug/debug-task.yaml
oc delete -f $root_folder/debug/debug-task-run.yaml

oc apply -f $root_folder/debug/debug-task.yaml
oc apply -f $root_folder/debug/debug-task-run.yaml