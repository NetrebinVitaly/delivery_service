def pod =
"""
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: builder
    image: maven:3.9.9-eclipse-temurin-21-alpine
    command: ['cat']
    tty: true
"""

pipeline {
   agent {
       kubernetes {
           yaml pod
       }
   }

    stages {

        stage('Checkout') {
            steps {
                container('builder') {
                    checkout scm
                }
            }
        }

        stage('Build') {
            steps {
                container('builder') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Test') {
            steps {
                container('builder') {
                    sh '''
                    apk add --no-cache docker-cli
                    docker --version

                    mvn test
                    '''
                }
            }
        }
    }
}