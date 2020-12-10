// Jenkinsfile for tWAS App - CI/CD
def templateName = 'gse-twas'

openshift.withCluster() {
  env.NAMESPACE = openshift.project()
  env.APP_NAME = "${JOB_NAME}".replaceAll(/-build.*/, '')
  echo "Starting Pipeline for ${APP_NAME}..."
  env.BUILD = "${env.NAMESPACE}"
  env.DEV = "${APP_NAME}-dev"
  env.STAGE = "${APP_NAME}-stage"
  env.PROD = "${APP_NAME}-prod"
}

pipeline {
  agent {
    label "maven"
  }
  stages {
    stage('preamble') {
        steps {
            script {
                openshift.withCluster() {
                    openshift.withProject() {
                        echo "Using project: ${openshift.project()}"
                        echo "APPLICATION_NAME: ${params.APPLICATION_NAME}"
                    }
                }
            }
        }
    }
    // Build Application using Maven
    stage('Maven build') {
      steps {
        sh """
        env
        mvn -v 
        cd CustomerOrderServicesProject
        mvn clean package
        """
      }
    }
      
    // Run Maven unit tests
    stage('Unit Test'){
      steps {
        sh """
        mvn -v 
        cd CustomerOrderServicesProject
        mvn test
        """
      }
    }
      
    // Build Container Image using the artifacts produced in previous stages
    stage('Build tWAS App Image'){
      steps {
        script {
          // Build container image using local Openshift cluster
          openshift.withCluster() {
            openshift.withProject() {
              timeout (time: 10, unit: 'MINUTES') {
                // run the build and wait for completion
                def build = openshift.selector("bc", "${params.APPLICATION_NAME}").startBuild("--from-dir=.")
                                    
                // print the build logs
                build.logs('-f')
              }
            }        
          }
        }
      }
    } 
    stage('Promote to Dev') {
      steps {
        script {
          openshift.withCluster() {
            openshift.withProject() {
              openshift.tag("${env.BUILD}/${env.APP_NAME}:latest", "${env.DEV}/${env.APP_NAME}:latest")
            }
          }
        }
      }
    }

    stage('Promote to Stage') {
      steps {
        script {
          openshift.withCluster() {
            openshift.withProject() {
              openshift.tag("${env.DEV}/${env.APP_NAME}:latest", "${env.STAGE}/${env.APP_NAME}:latest")
            }
          }
        }
      }
    }

    stage('Promotion gate') {
      steps {
        script {
          input message: 'Promote application to Production?'
        }
      }
    }

    stage('Promote to Prod') {
      steps {
        script {
          openshift.withCluster() {
            openshift.withProject() {
              openshift.tag("${env.STAGE}/${env.APP_NAME}:latest", "${env.PROD}/${env.APP_NAME}:latest")
            }
          }
        }
      }
    }
  }
}