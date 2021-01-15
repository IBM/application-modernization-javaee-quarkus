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


### From WebSphere Traditional 8.5.5 to WebSphere Traditional 9

Open http://localhost:3000/ and create workspace 'workspace' and collection 'collection855'.

Download the data collector for Linux 'transformationadvisor-Linux_workspace_collection855.tgz' and copy it to ${ROOT_FOLDER}/transformation-advisor/tool.

Launch WebSphere Traditional application (without database):

```
$ sh ${ROOT_FOLDER}/scripts/install-dojo.sh
$ sh ${ROOT_FOLDER}/scripts/install-was-dependencies.sh
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-app-was855.sh
```

```
$ docker cp ${ROOT_FOLDER}/transformation-advisor/tool/transformationadvisor-Linux_workspace_collection855.tgz storefront-was855:/tmp
$ docker exec -it storefront-was855 /bin/bash
```

```
$ cd /tmp
$ tar xvfz transformationadvisor-Linux_workspace_collection855.tgz
$ exit
```

```
$ docker cp ${ROOT_FOLDER}/transformation-advisor/wast855-to-wast90/customCmd.properties storefront-was855:/tmp/transformationadvisor-2.4.0/conf
$ docker exec -it storefront-was855 /bin/bash
```

```
$ cd /tmp/transformationadvisor-2.4.0
$ ./bin/transformationadvisor -w /opt/IBM/WebSphere/AppServer -p AppSrv01
$ exit
```

Usually the results are uploaded to Transformation Avisor automatically. If this doesn't work, you can do this manually.

```
$ cd ${ROOT_FOLDER}/transformation-advisor/wast855-to-wast90
$ docker cp storefront-was855:/tmp/transformationadvisor-2.4.0/AppSrv01.zip .
```


### From WebSphere Traditional 9 to WebSphere Liberty

Open http://localhost:3000/ and create workspace 'workspace' and collection 'collection90'.

Download the data collector for Linux 'transformationadvisor-Linux_workspace_collection90.tgz' and copy it to ${ROOT_FOLDER}/transformation-advisor/tool.

Launch WebSphere Traditional application (without database):

```
$ sh ${ROOT_FOLDER}/scripts/install-dojo.sh
$ sh ${ROOT_FOLDER}/scripts/install-was-dependencies.sh
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-app-was90.sh
```

```
$ docker cp ${ROOT_FOLDER}/transformation-advisor/tool/transformationadvisor-Linux_workspace_collection90.tgz storefront-was90:/tmp
$ docker exec -it storefront-was90 /bin/bash
```

```
$ cd /tmp
$ tar xvfz transformationadvisor-Linux_workspace_collection90.tgz
$ exit
```

```
$ docker cp ${ROOT_FOLDER}/transformation-advisor/wast-to-wasliberty/customCmd.properties storefront-was90:/tmp/transformationadvisor-2.4.0/conf
$ docker exec -it storefront-was90 /bin/bash
```

```
$ cd /tmp/transformationadvisor-2.4.0
$ ./bin/transformationadvisor -w /opt/IBM/WebSphere/AppServer -p AppSrv01
$ exit
```

Usually the results are uploaded to Transformation Avisor automatically. If this doesn't work, you can do this manually.

```
$ cd ${ROOT_FOLDER}/transformation-advisor/wast-to-wasliberty
$ docker cp storefront-was90:/tmp/transformationadvisor-2.4.0/AppSrv01.zip .
```
