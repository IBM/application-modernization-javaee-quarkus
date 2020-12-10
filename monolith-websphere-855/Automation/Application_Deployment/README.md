# Application Deployment

Firstly copy the **EAR** of your application into the **Application_Deployment** directory.

`cd Application_Deoplyment`

`chmod u+x install.sh`

Before running ./install.sh, open [install.sh](https://github.com/ibm-cloud-architecture/refarch-jee-customerorder/blob/was90-dev/Automation/Application_Deployment/install.sh), please fill in the details required and then run it.

`./install.sh`

That's it ... you are done with application_deployment. **install.sh** does all the magic. Let's have a look at it.

[install.sh](https://github.com/ibm-cloud-architecture/refarch-jee-customerorder/blob/was90-dev/Automation/Application_Deployment/install.sh)

This file contains all the parameters that are required for the application deployment. All the information required for the application deployment is defined in this installation file. IP Host information, WebSphere related paths, Application EAR location, Deployer information, Database connection information, Application related details are all defined in this file. You can have a look it [here](https://github.com/ibm-cloud-architecture/refarch-jee-customerorder/blob/was90-dev/Automation/Application_Deployment/install.sh) and change the parameters based on your requirements.

This the installation script that invokes all the jython scripts which deploys the application on the WebSphere. Let us see how this script works.

1.	Exports the IP host address.
2.	Exports the WebSphere Home and Profile paths. 
3.	Exports the location of the Application EAR, deployer email, database connection url, database schema, database username, and database password.
4.	Exports the application name, security group name, web endpoint name, test endpoint name and their urls.
5.	Calls the jython script for deploying the application ([app.jy](https://github.com/ibm-cloud-architecture/refarch-jee-customerorder/blob/was90-dev/Automation/Application_Deployment/app.jy)).
6.	Restarts the server.

If you have any changes in your application build, you can install the new EAR running this script again.
The jython script checks if the EAR is present. If not it installs the application. Otherwise, it uninstalls the application and then installs the application again.

Once this script gets executed successfully, application deployment will be done successfully in the WebSphere instance.
