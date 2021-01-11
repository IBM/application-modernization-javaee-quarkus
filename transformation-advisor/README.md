## Transformation Advisor Instructions

Clone the repo:

```
$ git clone https://github.com/nheidloff/application-modernization-javaee-quarkus.git && cd application-modernization-javaee-quarkus
$ ROOT_FOLDER=$(pwd)
```

Download Transformation Advisor: https://www.ibm.com/support/knowledgecenter/en/SS5Q6W/gettingStarted/deployTALocal.html

Copy transformationAdvisor.zip to ${ROOT_FOLDER}/transformation-advisor/tool and extract it.

Accept the licence terms, install and launch Transformation Advisor.

```
$ sh ${ROOT_FOLDER}/transformation-advisor/tool/transformationAdvisor/launchTransformationAdvisor.sh
```

Open http://localhost:3000/ and create workspace 'workspace' and collection 'collection'.

Download the data collector for Linux 'transformationadvisor-Linux_workspace_collection.tgz' and copy it to ${ROOT_FOLDER}/transformation-advisor/tool.

### From WebSphere Traditional 9 to WebSphere Liberty

to be done ...

Launch WebSphere Traditional application (without database):

```
$ sh ${ROOT_FOLDER}/scripts/install-dojo.sh
$ sh ${ROOT_FOLDER}/scripts/install-was-dependencies.sh
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-app-was90.sh
```

```
$ docker cp ${ROOT_FOLDER}/transformation-advisor/tool/transformationadvisor-Linux_workspace_collection.tgz storefront-was90:/tmp
$ docker exec -it storefront-was90 /bin/bash
```

```
$ cd /tmp
$ tar xvfz transformationadvisor-Linux_workspace_collection.tgz
$ exit
```

```
$ docker cp ${ROOT_FOLDER}/transformation-advisor/wast-to-wasliberty/customCmd.properties storefront-was90:/tmp/transformationadvisor-2.4.0/conf
$ docker exec -it storefront-was90 /bin/bash
```

```
$ cd /tmp/transformationadvisor-2.4.0
$ ./bin/transformationadvisor -w /opt/IBM/WebSphere/AppServer -p AppSrv01 wsadmin passw0rd
$ exit
```

```
$ cd ${ROOT_FOLDER}/transformation-advisor/wast-to-wasliberty
$ docker cp storefront-was90:/tmp/transformationadvisor-2.4.0/AppSrv01.zip .
```
