# Db2

Author: David Vande Pol (IBM)

To build the db2 container that is required for Customer Order Service application simply RUN

`docker build . -t storefront-db2`

This loads the script populateDB.sh which will populate the database after creation.

To run the database run the command


`docker run --name storefront-db2 --privileged=true -e LICENSE=accept  -e DB2INST1_PASSWORD=db2inst1 -e DBNAME=orderdb -p 50000:50000 storefront-db2`

If you want to connect to the database from another container you have to run on the same network.

`docker create network store-front-network`

`docker run --name storefront-db2 --network store-front-network --privileged=true -e LICENSE=accept  -e DB2INST1_PASSWORD=db2inst1 -e DBNAME=orderdb -p 50000:50000 storefront-db2`

Then in the tWAS container we would include `--network store-front-network` reference the database using hostname `torefront-db2`.
