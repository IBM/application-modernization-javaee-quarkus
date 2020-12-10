# Server Configuration

`cd Server_configuration`

`chmod u+x install.sh`

Before running **./install.sh**, open [install.sh](https://github.com/ibm-cloud-architecture/refarch-jee-customerorder/blob/was90-dev/Automation/Server_Configuration/install.sh), please fill in the details required and then run it.

`./install.sh`

That's it ... you are done with server configuration. **install.sh** does all the magic. Let's have a look at it.
 
[install.sh](https://github.com/ibm-cloud-architecture/refarch-jee-customerorder/blob/was90-dev/Automation/Server_Configuration/install.sh)

This file contains all the parameters that are required for the configuration. All the information required for the server configuration is defined in this installation file. WebSphere related paths, LDAP Configuration parameters, J2C authentication alias information, JDBC provider and data source related information, JPA specification, JAX-RS specifications are all defined in this file. You can take a look at it [here](https://github.com/ibm-cloud-architecture/refarch-jee-customerorder/blob/was90-dev/Automation/Server_Configuration/install.sh) and change the parameters based on your requirements.

This is the installation script that invokes all the jython scripts which configures the Server. Let us see how this script works.

1.	Exports the WebSphere Home and Profile paths. 
2.	Calls the jython script for enabling the [Application Security](https://github.com/ibm-cloud-architecture/refarch-jee-customerorder/blob/was90-dev/Automation/Server_Configuration/AppSecurity.jy).
3.	Exports the LDAP related information namely the host, port, server type, bind dn,bind password, primary admin id, dn, and ldap server password.
4.	Calls an inbuilt script openFirewallPorts.sh which opens the ports 50000 (used by DB) and 17830 (used by LDAP).
5.	Calls the jython script for [LDAP Configuration](https://github.com/ibm-cloud-architecture/refarch-jee-customerorder/blob/was90-dev/Automation/Server_Configuration/LDAPConfig.jy).
6.	Stops the server.
7.	Now that we are connected to remote LDAP, restarts the server to connect using new credentials.
8.	Exports the J2C authentication alias information such as username, password, and authentication alias.
9.	Calls the jython script for configuring the [j2c authentication alias](https://github.com/ibm-cloud-architecture/refarch-jee-customerorder/blob/was90-dev/Automation/Server_Configuration/j2cauth.jy) information.
10.	Steps 8 and 9 can be repeated based upon the number of j2c authentication alias you want to configure.
11.	Exports the JDBC provider and data source related information namely driver path, DB2 host, DB2 database name, DB2 port, datasource name, datasource JNDI, and Authentication alias.
12.	Calls the jython script for configuring the [JDBC provider and DataSources](https://github.com/ibm-cloud-architecture/refarch-jee-customerorder/blob/was90-dev/Automation/Server_Configuration/DataSource.jy).
13.	Steps 11 and 12 can be repeated based upon the number of DataSources you want to configure.
14.	Calls the jython script for configuring the [JPA and JAX-RS specifications](https://github.com/ibm-cloud-architecture/refarch-jee-customerorder/blob/was90-dev/Automation/Server_Configuration/specs.jy).
15.	Restarts the server.

Once this script gets executed successfully, all the server related configurations will be done in the WebSphere instance.

