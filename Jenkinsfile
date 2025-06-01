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
    volumeMounts:
        - name: docker-sock
          mountPath: /var/run/docker.sock
    env:
    - name: DOCKER_HOST
      value: "tcp://localhost:2375"
    - name: TESTCONTAINERS_HOST_OVERRIDE
      value: "localhost"
    - name: TESTCONTAINERS_RYUK_DISABLED
      value: "true"
  - name: dind
    image: docker:dind
    securityContext:
      privileged: true
    args: ["--host", "tcp://0.0.0.0:2375", "--tls=false"]
    volumeMounts:
        - mountPath: /var/run/docker.sock
          name: docker-sock
  volumes:
    - name: docker-sock
      emptyDir: {}
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