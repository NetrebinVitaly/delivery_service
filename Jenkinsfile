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
    env:
    - name: DOCKER_HOST
      value: "tcp://dind:2375"
  - name: dind
    image: docker:dind
    securityContext:
      privileged: true
    args: ["--host", "tcp://dind:2375", "--tls=false"]
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
                    mvn test -Ddocker.host=tcp://localhost:2375
                    '''
                }
            }
        }
    }
}