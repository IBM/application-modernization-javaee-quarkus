**Transformation Advisor**

Download and extract
https://www.ibm.com/support/knowledgecenter/en/SS5Q6W/gettingStarted/deployTALocal.html

Launch
/Users/nheidloff/Desktop/modernization/transformation-advisor
./launchTransformationAdvisor.sh

http://192.168.178.50:3000

Download transformationadvisor-Linux_nd_2_wt_collection.tgz

docker cp /Users/nheidloff/Downloads/transformationadvisor-Linux_nd_2_wt_collection.tgz 85df832abfa8:/work/transformationadvisor-Linux_nd_2_wt_collection.tgz

tar xvfz transformationadvisor-Linux_nd_2_wt_collection.tgz

vi conf/customCmd.properties 

comment out gettext.sh

was9 to liberty:
./bin/transformationadvisor -w /opt/IBM/WebSphere/AppServer -p AppSrv01 wsadmin passw0rd

docker cp 85df832abfa8:/work/transformationadvisor-2.2.0/AppSrv01.zip /Users/nheidloff/Desktop/modernization/transformation-advisor/output-was9-ol/AppSrv01.zip

was8 to was9:
./bin/transformationadvisor -w /opt/IBM/WebSphere/AppServer -p AppSrv01 wasadmin passw0rd

docker cp 85df832abfa8:/work/transformationadvisor-2.2.0/AppSrv01.zip /Users/nheidloff/Desktop/modernization/transformation-advisor/output-was8-was9/AppSrv01.zip

transformation-advisor nheidloff$ ./scripts/stopTransformationAdvisor.sh
