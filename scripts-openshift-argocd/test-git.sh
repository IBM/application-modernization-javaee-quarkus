#!/bin/bash
ROOT_FOLDER=$(cd $(dirname $0); cd ..; pwd)
exec 3>&1

function runScript() {
  GITHUB_NAME=nheidloff

  curl -u $GITHUB_NAME https://api.github.com/repos/IBM/plex/forks -d ''
  #ssh -T git@github.com:IBM/plex.git
  #git clone git@github.com:IBM/plex.git
}

runScript