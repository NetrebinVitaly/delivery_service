pipeline {
    agent {
        kubernetes {
          label 'jenkins-agent'
          yaml """
            apiVersion: v1
            kind: Pod
            namespace: develop-tools
            spec:
                containers:
                - name: maven
                 image: maven:3.9.9-eclipse-temurin-24-alpine
                 command: ['cat']
                 tty: true
                 volumeMounts:
                - name: docker-sock
                 mountPath: /var/run/docker.sock
                - name: dind
                 image: docker:dind
                 securityContext:
                    privileged: true
                    volumeMounts:
                - name: docker-sock
                 mountPath: /var/run/docker.sock
                volumes:
                - name: docker-sock
                 emptyDir: {}

            """
        }
      }
        stage('Build') {
            steps {
                container('maven'){
                    sh 'mvn clean package -DskipTests'
                }

            }
        }
        stage('Test') {
            steps {
                container('maven'){
                    sh '''
                        apk add --no-cache docker
                        export DOCKER_HOST=unix:///var/run/docker.sock
                        mvn test
                    '''
                }
            }
        }
    }
}

