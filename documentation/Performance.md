## Performance and Memory Tests

### Test Case 1: Open Liberty

* Open Liberty application (CDI; modern project structure) launched locally via 'mvn liberty:dev'
* Db2 running in container
* Synchronous database access via JPA and Db2 driver

```
$ git clone https://github.com/nheidloff/application-modernization-javaee-quarkus.git && cd application-modernization-javaee-quarkus
$ ROOT_FOLDER=$(pwd)
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-db2.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-database-postgres-catalog.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-kafka.sh
$ sh ${ROOT_FOLDER}/scripts-docker/run-monolith-db2.sh first
```

curl "http://localhost:9088/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2"

