## Configuration Automation

[**was_config_jython.sh**](https://github.com/ibm-cloud-architecture/refarch-jee-customerorder/blob/was90-dev/Common/WAS_Config/was_config_jython.sh)

This is the shell script that generates the Websphere Configuration jython script taking the required inputs from the user.

[**was.properties**](https://github.com/ibm-cloud-architecture/refarch-jee-customerorder/blob/was90-dev/Common/WAS_Config/was.properties)

Place all the paths of your drivers in this file. This contains DB2JCC4, DB2JCC_LICENSE_CISUZ, DB2JCC_LICENSE_CU, and WAS_INSTALL_ROOT paths.

[**WAS_config.py**](https://github.com/ibm-cloud-architecture/refarch-jee-customerorder/blob/was90-dev/Common/WAS_Config/WAS_config.py)

This is the final automation jython script. This script configures the WebSphere Environment.

### Steps

1. Replace the paths in the **was.properties** file with your paths.

2. Run this command. `sh was_config_jython.sh -f was.properties`

This script walks the user through some steps. 

For Users and Groups, it asks the options (default or LDAP). Choose **default**. 
    
It prompts you for the number of authentication aliases.
    
It asks you for JAAS Authentication Data. The it prompts you for the number of datasources and the datasource information too.

Please enter the neccessary information.

3. Once the script completes its execution, **WAS_config.py** file will be generated.

4. Now, start your WebSphere Server.

5. Go to the <WAS_PROFILE_DIR>/bin, and use the following command.

`<Profile Home>/bin/wsadmin.(bat/sh) –lang jython –f <Location of Jython script>`

6. Once the script gets executed successfully, the configuration of WAS is done.

7. You can verify the configuration by opening your admin console and then check if all the resources are correct.
