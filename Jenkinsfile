def pod =
"""
apiVersion: v1
kind: Pod
metadata:
 labels:
   name: worker
spec:
 serviceAccountName: jenkins-admin
 containers:
   - name: java17
     image: eclipse-temurin:17.0.9_9-jdk-jammy
     resources:
       requests:
         cpu: "1000m"
         memory: "2048Mi"
     imagePullPolicy: Always
     tty: true
     command: ["cat"]
   - name: dind
     image: docker:dind
     imagePullPolicy: Always
     tty: true
     env:
       - name: DOCKER_TLS_CERTDIR
         value: ""
     securityContext:
       privileged: true
"""

pipeline {
   agent {
       kubernetes {
           yaml pod
       }
   }
   environment {
       DOCKER_HOST = 'tcp://localhost:2375'
       DOCKER_TLS_VERIFY = 0
   }

   stages {

       stage('Build') {
           steps {
               container('maven'){
                   sh 'mvn clean package -DskipTests'
               }
           }
       }
       stage('Test') {
           steps {
               container('java17') {
                   script {
                       sh "./mvnw test"
                   }
               }
           }
       }
   }
}