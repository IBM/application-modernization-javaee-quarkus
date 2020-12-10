/**
* Jenkins Doc: https://jenkins.io/doc/
*
**/

openshift.withCluster() {
  env.NAMESPACE = openshift.project()
  env.APP_NAME = "${JOB_NAME}".replaceAll(/-build.*/, '')
  env.BUILD = "${env.NAMESPACE}"
  env.BASE_IMAGE_REPO = "ibmcom/websphere-traditional"
  env.BASE_IMAGE_TAG = "9.0.0.11"
  env.REGISTRY = "docker-registry.default.svc:5000"
  env.DEV_PROJECT = "dev"
  env.DEV_IMAGE_TAG = "${env.REGISTRY}/${env.DEV_PROJECT}/${env.APP_NAME}:${env.BUILD_NUMBER}"
  
  echo "Starting Pipeline for ${APP_NAME}..."
}

pipeline {
    agent {
      label "maven"
    }
    stages {
      
      
      // Build Container Image 
      stage('Build tWAS App Image'){
                steps {
                      script {
                              // Build container image using local Openshift cluster
                              openshift.withCluster() {
                                openshift.withProject(env.DEV_PROJECT) {
                                  timeout (time: 10, unit: 'MINUTES') {
                              // Generate the imagestreams and buildconfig
                                    def src_image_stream = [
                                      "apiVersion": "v1",
                                      "kind": "ImageStream",
                                      "metadata": [
                                        "name": "websphere-traditional",
                                      ],
                                      "spec": [
                                        "tags": [
                                          [
                                            "name": "9.0.0.11",
                                            "from": [
                                              "kind": "DockerImage",
                                              "name": "${env.BASE_IMAGE_REPO}:${env.BASE_IMAGE_TAG}"
                                            ]
                                          ]
                                        ]
                                      ]
                                    ]
                                    def sImageStream = openshift.apply(src_image_stream)
                                    println "Source ImageStream Created........ ${sImageStream.names()}"

                                    def target_image_stream = [
                                      "apiVersion": "v1",
                                      "kind": "ImageStream",
                                      "metadata": [
                                          "name": "${env.APP_NAME}",
                                      ]
                                    ]
                                    def tImageStream =openshift.apply(target_image_stream)
                                    println "Target ImageStream Created........ ${tImageStream.names()}"
                                    
                                    // BuildConfig that uses Source & Target ImageStreams
                                    def buildconfig = [
                                      "apiVersion": "v1",
                                      "kind": "BuildConfig",
                                      "metadata": [
                                        "name": "${env.APP_NAME}",
                                      ],
                                      "spec": [
                                        "output": [
                                          "to": [
                                            "kind": "ImageStreamTag",
                                            "name": "${env.APP_NAME}:${env.BUILD_NUMBER}"
                                          ]
                                        ],
                                        "source": [
                                          "type": "Binary"
                                        ],
                                        "strategy": [
                                          "dockerStrategy": [
                                            "from": [
                                              "kind": "ImageStreamTag",
                                              "name": "websphere-traditional:9.0.0.11"
                                            ],
                                            "dockerfilePath": "./session-mgmt/Dockerfile",
                                            "noCache": true,
                                            "forcePull": true
                                          ]
                                        ],
                                        "failedBuildsHistoryLimit": 3,
                                        "successfulBuildsHistoryLimit": 3
                                      ]
                                    ]
                                    def bConfig = openshift.apply(buildconfig)
                                    println "BuildConfig Created........ ${bConfig.names()}"

                                    // run the build and wait for completion
                                    def build = openshift.selector("bc", env.APP_NAME).startBuild("--from-dir=.")
                                    
                                    // print the build logs
                                    build.logs('-f')
                                  }
                                }        
                              }
                            }
                          }
                        }    
      stage('Deploy to DEV') {
        steps {
             script {
                   openshift.withCluster() {
                   openshift.withProject(env.DEV_PROJECT) {
                     sh """
                        sed -i -e 's#docker-registry.default.svc:5000/dev/sessions-twas:1.0#${env.DEV_IMAGE_TAG}#' session-mgmt/yaml/dc.yaml
                        """
                   def dc_stage = openshift.apply( readFile("session-mgmt/yaml/dc.yaml"))
                   println "Created objects: ${dc_stage.names()}"
                   def service_stage = openshift.apply( readFile("session-mgmt/yaml/service.yaml"))
                   println "Created objects: ${service_stage.names()}"
                   }
                  }
                 }
               }
             }
          
     }
  }
