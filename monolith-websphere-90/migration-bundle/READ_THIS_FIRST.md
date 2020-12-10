## Generated Artifacts
| Artifact | Description |
| --- | --- |
| CustomerOrderServicesApp.ear | Application binary ear, or war or jar file |
| Dockerfile | Dockerfile to build the application container image |
| src/config/install_app.py | Jython script to install the application |
| src/config/server_config.py | server configuration Jython script file |  
| lib/ | Directory for external library dependencies, such as database driver library | 
| pipeline/k8s/ | Directory for the application deployment manifest files |
| pipeline/tekton/ | Directory for Tekton pipeline manifest files |

### Notes
- The generated `src/config/server_config.py` script file may contain password variable but its value has been stripped off from
the original source environment.  You need to provide it manually. For example, 

  ```
  # The following variables are used to replace sensitive data in the configuration for the application.
  # The values for these variables were not collected because the includeSensitiveData option was not specified.
  # ============================================================
  myhost_a1CellManager01_db2_password_1=''
  # ============================================================  
  ```
- Verify the git url value in `pipeline/tekton/resources.yaml` which should be set correctly if the migration bundle
  is send to git directly from the Transformation Advisor user interface. 

## Deploy to Red Hat OpenShift Container Platform using Tekton pipeline
1. Clone this git repository to your local machine
2. Login to OCP cluster with default project namespace.
   ```
   oc login --token={your_openshift_cluster_access_token} --server=your_openshift_cluster_url:6443
   oc project default
   ```
3. Create a Tekton pipeline and pipeline resources for building and deploying this application
   ```
   oc apply -f pipeline/tekton/pipeline.yaml
   oc apply -f pipeline/tekton/resources.yaml
   ```
   
   **note:** Update the image url value in `pipeline/tekton/resources.yaml` if the application is deployed to a 
   different project namespace (e.g. *myproject*).  
   ```
     params:
       - name: url
         value: image-registry.openshift-image-registry.svc:5000/myproject/application:latest
   ```
4. Follow the instruction to install the Tekton pipeline CLI from https://github.com/tektoncd/cli

5. Start the pipeline using the Tekton dashboard or using the following Tekton pipeline CLI command:
   ``` 
   tkn pipeline start build-and-deploy
   ```
   To install the Tekton pipeline CLI, follows instructions in https://github.com/tektoncd/cli

      
6. After the pipeline run has successfully finished, find the application 
   route from Openshift dashboard or the following command.
   ```
   oc get routes
   ```

## Basic authentication (Git)
Define a secret containing the username and access token to the github repository if the repository is a private repository or GitHub enterprise repository.

- create a secret.yaml file and replace the username and password value
  ```
  apiVersion: v1
  kind: Secret
  type: kubernetes.io/basic-auth
  metadata:
    annotations:
      tekton.dev/git-0: https://github.com   # or change to your GHE url, e.g. https://github.enterprise.com
    name: ghe-token
    labels:
      serviceAccount: pipeline
  data:
    password: base64_encoded_github_access_token
    username: base64_encode_github_user_id
  ```
- create the github authentication secret
  ```
  oc apply -f secret.yaml 
  ```
- link the secret to the `pipeline` service account
  ```
  oc secrets link pipeline ghe-token
  ```
- start the Tekton pipeline run with the `pipeline` service account
  ```
  tkn pipeline start build-and-deploy -s pipeline
  ```
