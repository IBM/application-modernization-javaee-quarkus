# Db2

Author: David Vande Pol (IBM)


```
$ docker build . -t storefront-db2
```

```
$ docker run --name storefront-db2 --privileged=true -e LICENSE=accept  -e DB2INST1_PASSWORD=db2inst1 -e DBNAME=orderdb -p 50000:50000 storefront-db2
```

```
$ docker create network store-front-network
$ docker run --name storefront-db2 --network store-front-network --privileged=true -e LICENSE=accept  -e DB2INST1_PASSWORD=db2inst1 -e DBNAME=orderdb -p 50000:50000 storefront-db2
```


### Image with Data

Author: Niklas Heidloff

```
$ docker run --name storefront-db2 --network store-front-network --privileged=true -e LICENSE=accept -e DB2INST1_PASSWORD=db2inst1 -e DBNAME=orderdb -p 50000:50000 storefront-db2
```

```
$ docker cp storefront-db2:/database /Users/niklasheidloff/git/application-modernization-javaee-quarkus/db2
```

```
$ docker build . -t nheidloff/storefront-db2-with-data -f DockerfileWithData
$ docker push nheidloff/storefront-db2-with-data
```

```
$ docker run -it --name storefront-db2 -p 50000:50000 -e LICENSE=accept -e DB2INST1_PASSWORD=db2inst1 --privileged=true --network store-front-network nheidloff/storefront-db2-with-data
```