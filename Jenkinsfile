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
               - name: jnlp
                 image: jenkins/inbound-agent:latest
                    args: [
                        "-url", "http://jenkins-controller:8080",
                        "-secret", "<AGENT_SECRET>",
                        "-name", "jenkins-agent"
                    ]
              - name: maven
                image: maven:3.9.9-eclipse-temurin-24-alpine
                command: ['cat']
                tty: true
            """
        }
      }
    stages {
        stage('Build') {
            steps {
                container('maven'){
                    sh 'mvn clean package'
                }

            }
        }
        stage('Test') {
            steps {
                container('maven'){
                    sh 'mvn test'
                }
            }
        }
    }
}

