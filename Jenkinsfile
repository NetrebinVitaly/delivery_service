def pod =
"""
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: builder
    image: alpine:3.19
    command: ['cat']
    tty: true
    resources:
      requests:
        cpu: "1000m"
        memory: "2Gi"
      limits:
        cpu: "2000m"
        memory: "4Gi"
    env:
    - name: MAVEN_OPTS
      value: "-Xmx1G"
    - name: DOCKER_HOST
      value: "tcp://localhost:2375"
    volumeMounts:
    - mountPath: /var/run/docker.sock
      name: docker-sock
  - name: docker-daemon
    image: docker:dind
    securityContext:
      privileged: true
    env:
    - name: DOCKER_TLS_CERTDIR
      value: ""
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
   environment {
       TESTCONTAINERS_RYUK_DISABLED = 'true'
       TESTCONTAINERS_CHECKS_DISABLE = 'true'
   }

    stages {
        stage('Setup Environment') {
            steps {
                container('builder') {
                    sh '''
                    apk add --no-cache openjdk17 maven docker-cli
                    # Настройка Testcontainers
                    mkdir -p ~/.testcontainers.properties
                    echo "ryuk.container.privileged=true" > ~/.testcontainers.properties
                    echo "docker.host=tcp://localhost:2375" >> ~/.testcontainers.properties
                    '''
                }
            }
        }

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
                    sh 'mvn test'
                }
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Publish Results') {
            steps {
                container('builder') {
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }

}