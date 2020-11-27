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
