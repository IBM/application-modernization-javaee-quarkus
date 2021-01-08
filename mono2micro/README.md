## Mono2Micro Instructions

Download Mono2Micro: http://ibm.biz/Mono2Micro 

Put three files in ${ROOT_FOLDER}/mono2micro/tool. Extract the two zip files.

```
$ docker run --rm -it -v ${ROOT_FOLDER}/:/var/application ibmcom/mono2micro-bluejay /var/application/monolith-open-liberty
$ cp ${ROOT_FOLDER}/monolith-open-liberty-klu/refTable.json ${ROOT_FOLDER}/mono2micro/output/tables
$ cp ${ROOT_FOLDER}/monolith-open-liberty-klu/symTable.json ${ROOT_FOLDER}/mono2micro/output/tables
```

```
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-monolith-db2.sh
$ sh ${ROOT_FOLDER}/scripts-docker/build-and-run-splitted-frontend-open-klu.sh
```

Open http://localhost/CustomerOrderServicesWeb

```
$ cd ${ROOT_FOLDER}/mono2micro/tool/Mono2Micro-Monolith-DataCollector/Flicker
$ java -cp commons-net-3.6.jar:json-simple-1.1.jar:. Flicker -no_ntp -a context.json
```

Enter <Label> to start recording current context (type "Exit" to quit).  
show-shop  
Enter STOP to terminate the recording of the current context.  
STOP  

Enter <Label> to start recording current context (type "Exit" to quit).  
change-category  
Enter STOP to terminate the recording of the current context.  
STOP 

Enter <Label> to start recording current context (type "Exit" to quit).  
add-to-cart  
Enter STOP to terminate the recording of the current context.  
STOP  

Enter <Label> to start recording current context (type "Exit" to quit).  
show-order  
Enter STOP to terminate the recording of the current context.  
STOP  

Enter <Label> to start recording current context (type "Exit" to quit).  
show-account  
Enter STOP to terminate the recording of the current context.  
STOP
     
Enter <Label> to start recording current context (type "Exit" to quit).  
exit

```
$ cp ${ROOT_FOLDER}/mono2micro/tool/Mono2Micro-Monolith-DataCollector/Flicker/context.json ${ROOT_FOLDER}/mono2micro/output/contexts
$ docker cp storefront-backend-open:/logs/messages.log .
$ cp messages.log ${ROOT_FOLDER}/mono2micro/output/logs
```

```
$ docker run --rm -it -v ${ROOT_FOLDER}/mono2micro/output:/var/application ibmcom/mono2micro-aipl
```