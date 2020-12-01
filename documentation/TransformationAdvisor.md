**Transformation Advisor**

Download and extract
https://www.ibm.com/support/knowledgecenter/en/SS5Q6W/gettingStarted/deployTALocal.html

Launch
/Users/nheidloff/Desktop/modernization/transformation-advisor
./launchTransformationAdvisor.sh

http://192.168.178.27:3000

Create workspace 'workspace' and connection 'collection'.

Download transformationadvisor-Linux_workspace_collection.tgz

```
$ git clone https://github.com/nheidloff/application-modernization-javaee-quarkus.git
$ cd application-modernization-javaee-quarkus
$ sh scripts/install-dojo.sh
$ sh scripts-docker/build-and-run-monolith-db2.sh
$ sh scripts-docker/build-and-run-monolith-app.sh
```

docker cp /Users/nheidloff/git/application-modernization-javaee-quarkus/transformation-advisor/transformationadvisor-Linux_workspace_collection.tgz storefront-monolith:/tmp/transformationadvisor-Linux_workspace_collection.tgz

docker exec -it storefront-monolith /bin/bash

cd tmp/

tar xvfz transformationadvisor-Linux_workspace_collection.tgz

cd /tmp/transformationadvisor-2.3.0/


**Question**
How can you run TA? I want to use it to convert fom WebSphere Liberty to Open Liberty.

cat /config/configDropins/defaults/keystore.xml

./bin/transformationadvisor -w /opt/ibm/wlp/usr/servers/defaultServer -p AppSrv01 wsadmin EA5EsludBELW9EoUkZ3zD3FWf09ob1c89dUjaPqnJqg=

Documentation: https://www.ibm.com/support/knowledgecenter/en/SS5Q6W/gettingStarted/usingDataCollector.html


vi conf/customCmd.properties 

comment out gettext.sh

