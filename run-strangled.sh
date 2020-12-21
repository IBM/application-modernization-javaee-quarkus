set -x
ROOT_FOLDER=$(pwd)
sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-db2.sh
sh ${ROOT_FOLDER}/scripts-docker/run-database-postgres-catalog.sh
sh ${ROOT_FOLDER}/scripts-docker/run-kafka.sh
sh ${ROOT_FOLDER}/scripts-docker/build-and-run-all-quarkus.sh
