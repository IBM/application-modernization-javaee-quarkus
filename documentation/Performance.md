## Performance and Memory Tests

### Test Case 1: Open Liberty

* Open Liberty application launched locally via 'mvn liberty:dev'
* Db2 running in a container
* Synchronous database access via JPA and Db2 driver

```
$ git clone https://github.com/nheidloff/application-modernization-javaee-quarkus.git && cd application-modernization-javaee-quarkus
$ ROOT_FOLDER=$(pwd)
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-db2.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-database-postgres-catalog.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-kafka.sh
$ sh ${ROOT_FOLDER}/scripts/run-openliberty-cloud-native-locally.sh
```

curl "http://localhost:9088/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2"


### Test Case 2: Quarkus - JVM Mode

* Quarkus application
* Postgres running in a container
* Reactive endpoints and reactive database access

```
$ git clone https://github.com/nheidloff/application-modernization-javaee-quarkus.git && cd application-modernization-javaee-quarkus
$ ROOT_FOLDER=$(pwd)
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-db2.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-database-postgres-catalog.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-kafka-local.sh
$ sh ${ROOT_FOLDER}/scripts/run-quarkus-jvm-locally.sh
```

curl "http://localhost:8082/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2"
