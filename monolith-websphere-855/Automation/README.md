# AUTOMATION … Developer’s dearest friend

Are you tired of configuring the WebSphere manually? Do you want to save time on manual tasks? 

If the answer is YES … then just AUTOMATE it. If you want to save your time, you should automate it. For me to manually configure the server and install the application, it takes almost 10-20 minutes. Using automation, I can do the same thing in less than 5 minutes. 

## WebSphere On the Cloud in 8 simple steps

### Step 1: Getting the project repository

You can clone the repository from its main GitHub repository page and checkout the appropriate branch for this version of the application.

1. `git clone https://github.com/ibm-cloud-architecture/refarch-jee-customerorder.git`  
2. `cd refarch-jee-customerorder`  
3. `git checkout was90-dev`

### Step 2: Create DB2 service instance for ORDERDB

1. Go to your [Bluemix console](https://new-console.ng.bluemix.net/) and create an instance of [`Db2 on Cloud SQL DB (formerly dashDB TX)`](https://console.bluemix.net/catalog/services/db2-on-cloud-sql-db-formerly-dashdb-tx) and name it `DB2 on Cloud - ORDERDB`
2. When you are redirected back to your Services dashboard, click on the new database service instance.
2. Click on `Service Credentials` and then click on `New credential`.
3. Click `Add` and then click on `View credentials`.
4. Make note of the `password` field for your instance.
5. Click on `Manage` and then click on `Open`.
6. Click on `Run SQL`
7. Click on `Open Script` and browse to `createOrderDB.sql` inside the 'Common' sub-directory of the project directory.
8. Click `Run All`
9. You should see some successes and some failures.  This is due to the scripts cleaning up previous data, but none exists yet.  You should see 28 successful SQL statements and 30 failures.
10. Go to the dropdown in the upper right and click on `Connection Info`
11. Select `Without SSL` and copy the following information for later:
- Host name _(most likely in the form of dashdb-txn-flex-yp-dalXX-YY.services.dal.bluemix.net)_
- Port number _(most likely 50000)_
- Database name _(most likely BLUDB)_
- User ID _(most likely bluadmin)_
- Password _(previous password from the `View credentials` tab)_

### Step 3: Create DB2 service instance for INVENTORYDB

1. Go to your [Bluemix console](https://new-console.ng.bluemix.net/) and create an instance of [`Db2 on Cloud SQL DB (formerly dashDB TX)`](https://console.bluemix.net/catalog/services/db2-on-cloud-sql-db-formerly-dashdb-tx) and name it `DB2 on Cloud - INVENTORYDB`
2. When you are redirected back to your Services dashboard, click on the new database service instance.
3. Click on `Service Credentials` and then click on `New credential`.
4. Click `Add` and then click on `View credentials`.
5. Make note of the `password` field for your instance.
6. Click on `Manage` and then click on `Open`.
7. Click on `Run SQL`
8. Click on `Open Script` and browse to `InventoryDdl.sql` inside the 'Common' sub-directory of the project directory.
9. Click `Run All`
10. You should see some successes and some failures.  This is due to the scripts cleaning up previous data, but none exists yet.  You should see 5 successful SQL statements and 4 failures.
11. Click on `Open Script` and browse to `InventoryData.sql` inside the 'Common' sub-directory of the project directory.  Confirm the prompt that you would like to open this new file and replace the previous content of the SQL Editor.
12. Click `Run All`
13.  You should now see 12 successes.
14. Go to the dropdown in the upper right and click on `Connection Info`
15. Select `Without SSL` and copy the following information for later:
- Host name _(most likely in the form of dashdb-txn-flex-yp-dalXX-YY.services.dal.bluemix.net)_
- Port number _(most likely 50000)_
- Database name _(most likely BLUDB)_
- User ID _(most likely bluadmin)_
- Password _(previous password from the `View credentials` tab)_

### Step 4: Create WebSphere Application Server service instance

1. Go to your [Bluemix console](https://new-console.ng.bluemix.net/) and create a [**WebSphere Application Server** instance](https://console-regional.ng.bluemix.net/catalog/services/websphere-application-server)

2. Name your service, choose the **WAS Base Plan**, and create the instance.

3. Once the service instance is created, provision a **WebSphere Version 9.0.0.0** server of size **Medium**.  This should be a server deployment taking up 2 of your 2 trial credits.  This step can take up to 30 minutes to complete.

4. Now you will access the WASaaS Admin Console to reconfigure your WAS cloud server. But first, you need to install and configure a VPN connection (if you have not already done so as a prerequisite):

A. Install the VPN Client

For Windows users, use the links provided on your WASaaS dashboard to download the installation executables for the latest versions of OpenVPN. Once downloaded, these files can be opened in that location. Follow the installation instructions. Install the VPN client using the following directory:

C:\Program Files\OpenVPN

For Linux users, use the link provided on your WASaaS dashboard and download the application binary for your version of Linux to install OpenVPN.

For Mac users, use the link provided on your WASaaS dashboard and follow the instructions to download and install OpenVPN.

Note: Please use the links provided in your Bluemix WASaaS dashboard.

B. Download and Extract the VPN Config Files

For Windows users, download the VPN configuration archive file using the **Download VPN Configuration** button on your WASaaS dashboard. From the download location, extract the files to the following required location:

`C:\Program Files\OpenVPN\Config`

For Linux and Mac users, download the VPN configuration archive file using the **Download VPN Configuration** button on your WASaaS dashboard. From the download location, extract the files to the following location:

`{OpenVPN home}\config`

C. Establish Your VPN Connection

For Windows users:

1. Right-click on the OpenVPN GUI icon on your desktop, select **Run as administrator**
2. Right-click on the OpenVPN GUI icon in your Windows system tray, select **Connect**

For Linux Users:

1. Go to **Network Manager** tray.
2. From the pop up menu, click on **VPN connections** and select the **Open VPN** connection.

For Mac users:

1. Click on the OpenVPN GUI icon in the Menu bar.
2. Click on the **connect wasaas-uss** to connect to OpenVPN.

Your VPN connection should now be established.

D. You should now be able to access the WASaaS Admin Console by selecting "Open the Admin Console" on your WASaaS dashboard.

5. Get a public IP address. This can be done using the [**Manage Public IP Access**](https://console.bluemix.net/docs/services/ApplicationServeronCloud/networkEnvironment.html#networkEnvironment) option.

6. You can **ssh** into the WebSphere Application Server instance using the Admin Username **root** and Password provided in your bluemix instance.

   In **Application Hosts/Nodes**, by expanding the Traditional **WebSphere Base option**, you can find the details of your OS distribution, Admin username, Admin password and Key Store password.

### Step 5: Getting the resources

1. We have provided a built EAR for installation on WAS V.9.0. It is available at https://github.com/ibm-cloud-architecture/refarch-jee/raw/master/static/artifacts/end-to-end-tutorial1/WAS9/CustomerOrderServicesApp-0.2.0-WAS9.ear for download.
2. Download the EAR and place it in **refarch-jee-customerorder > Automation > Application_Deployment**
3. Now, you have all the resources with you and let us put them on WAS instance.

**For Linux and MAC**
- Open your terminal.
- Run this command
  
  `scp -rp <Path of the Automation directory> root@<your-host>:/root` where **your_host** is the Host address of your WAS instance displayed in Application Hosts/Nodes section.
- This prompts you for the password. Please enter the password provided in your bluemix instance.
  In Application Hosts/Nodes, by expanding the Traditional WebSphere Base option, you can find the details of your OS distribution, Admin username, Admin password and Key Store password.

**For Windows**
- Open your command prompt.
- Download [pscp.exe](http://www.chiark.greenend.org.uk/~sgtatham/putty/download.html). 
  You can either go to the path where it got downloaded or set the path 
  `set path=C:\...\folder of pscp`
- Run this command

  `pscp -r <Path of the Automation directory> root@<your-host>:/root` where **your_host** is the Host address of your WAS instance displayed in Application Hosts/Nodes section.
- This prompts you for the password. Please enter the password provided in your bluemix instance.
  In Application Hosts/Nodes, by expanding the Traditional WebSphere Base option, you can find the details of your OS distribution, Admin username, Admin password and Key Store password.

Once done, you can see the resources getting copied to the WAS instance.

![Copy the Contents](https://github.com/ibm-cloud-architecture/refarch-jee/blob/master/static/imgs/automation/scp.png)

4. Now, ssh into the WebSphere Application Server instance using the Admin Username root and Password provided in your bluemix instance.
In Application Hosts/Nodes, by expanding the Traditional WebSphere Base option, you can find the details of your OS distribution, Admin username, Admin password and Key Store password.

`ls`

You should be able to see the **Automation** folder here.

If not, make sure you performed Step 3 correctly.

`cd Automation`

`ls`

Here you should be able to see **Application_Deployment** and **Server_Configuration** directories.

### Step 6: Perform WebSphere configuration

`cd Server_Configuration`

`chmod u+x install.sh`

Before doing **./install.sh**, if you are on a **Windows** system, please run `sed -i -e 's/\r$//' install.sh` because Linux uses the line feed character to mark the end of a line, whereas Windows uses the two-character sequence CR LF. Since we copied it from Winndows, the file has Windows line endings, which will confuse Linux.

`./install.sh`

![Server Configuration](https://github.com/ibm-cloud-architecture/refarch-jee/blob/master/static/imgs/automation/server.png)

At this point, if you want to log back into the Admin Console, you should use a different set of credentials, now that we're connected to the remote LDAP as our user registry.  Use the new credentials to login to the Admin Console.

Please have a look at the [Server Configuration scripts](https://github.com/ibm-cloud-architecture/refarch-jee-customerorder/tree/was90-dev/Automation/Server_Configuration) to know them in detail.

### Step 7: Install Customer Order Services application

`cd ..`

`cd Application_Deployment`

`chmod u+x install.sh`

Before doing **./install.sh**, if you are on a **Windows** system, please run `sed -i -e 's/\r$//' install.sh` because Linux uses the line feed character to mark the end of a line, whereas Windows uses the two-character sequence CR LF. Since we copied it from Winndows, the file has Windows line endings, which will confuse Linux.

`./install.sh`

![App Configuration](https://github.com/ibm-cloud-architecture/refarch-jee/blob/master/static/imgs/automation/app.png)

Please have a look at the [Application Deployment scripts](https://github.com/ibm-cloud-architecture/refarch-jee-customerorder/tree/was90-dev/Automation/Application_Deployment) to know them in detail.

### Step 8: Access Customer Order Services application

1.  Prime the database with the **JPA** tests available at **https://<i>your-host</i>:9443/CustomerOrderServicesTest** , where **your_host** is the **Host** address of your WAS instance displayed in **Application Hosts/Nodes** section.

2.  Login as the user `rbarcia` with the password of `bl0wfish`.  

3.  Select the checkboxes for the **org.pwte.example.jpa.test.CustomerOrderServicesTest** and **org.pwte.example.jpa.test.ProductSearchServiceTest** tests.  Then click **Run**.  

4.  You should see a screen with 10 successful tests.  If you do not, you should verify the connectivity information you updated during application installation.  This is available in the Admin Console via the `Applications > WebSphere enterprise applications > CustomerOrderServicesApp > Environment entries for Web modules` page.

5.  Access the application at **https://<i>your-host</i>:9443/CustomerOrderServicesWeb/#shopPage** , where **your_host** is the **Host** address of your WAS instance displayed in **Application Hosts/Nodes** section.

6.  Login as the user `rbarcia` with the password of `bl0wfish`.  

7.  Add an item to the cart by clicking on an available item.  Drag and drop the item to the cart.

8.  You should see the contents of your cart updated in the top right of the screen.
