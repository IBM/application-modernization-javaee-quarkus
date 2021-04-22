#!/bin/bash
ROOT_FOLDER=$(cd $(dirname $0); cd ..; pwd)
exec 3>&1

function runScript() {
  ssh -T git@github.com:IBM/plex.git
  git clone git@github.com:IBM/plex.git
}

runScript