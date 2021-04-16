#!/bin/bash

POD_NAME=$(oc get pods -n app-mod-tekton-dev | awk '/debug-task-run-pod/ {print $1;exit}')

kubectl exec -t -i $POD_NAME /bin/sh
