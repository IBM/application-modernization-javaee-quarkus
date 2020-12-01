### Work in Progress

to be done

**Monolith**

```
$ sh scripts/install-dojo.sh
$ sh scripts-docker/build-and-run-monolith-db2.sh
$ sh scripts-docker/build-and-run-monolith-app.sh
```

Open http://localhost/CustomerOrderServicesWeb


**Separate Frontend**

```
$ sh scripts/install-dojo.sh
$ sh scripts-docker/build-and-run-monolith-db2.sh
$ sh scripts-docker/build-and-run-splitted-frontend.sh
```

Open http://localhost/CustomerOrderServicesWeb


**Strangled Catalog Service**

```
$ sh scripts/install-dojo.sh
$ sh scripts-docker/build-and-run-monolith-db2.sh
$ sh scripts-docker/run-database-postgres-catalog.sh
$ sh scripts-docker/build-and-run-catalog.sh
```

Open http://localhost/CustomerOrderServicesWeb

Open http://localhost/explorer

Invoke these endpoints and check the logs:

```
$ curl http://localhost/CustomerOrderServicesWeb/jaxrs/Category
$ curl http://localhost/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2
$ curl -X PUT "http://localhost/CustomerOrderServicesWeb/jaxrs/Product/1" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"id\":1,\"categories\":[{\"id\":0,\"name\":\"string\",\"subCategories\":[null]}],\"description\":\"string\",\"image\":\"string\",\"name\":\"string\",\"price\":30}"
```


