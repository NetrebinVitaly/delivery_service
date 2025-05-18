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

-----BEGIN CERTIFICATE-----
MIIDBjCCAe6gAwIBAgIBATANBgkqhkiG9w0BAQsFADAVMRMwEQYDVQQDEwptaW5p
a3ViZUNBMB4XDTI1MDEwNjE1NDUwOVoXDTM1MDEwNTE1NDUwOVowFTETMBEGA1UE
AxMKbWluaWt1YmVDQTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMAY
s5o/7RrJyIG2rDx6iRLZBS1Mk87vlwmy3Z8ZdJ5axOh4tr7EaJ/5GTGkcwtQqRJx
4pnf/G4pN3nW8F+xtoyeEutdpfVZy9u3AB2ghw1gKt50BHJXU+PsbXkk44+1s/Om
yj5Ubjq61Ek8RsbKM+udFRFVRczvUWPMlRoXSrXU2xNGlAwJHnD9+0EYSjwiLb5u
VvxxzzYmFOh9bhUU4xZwT+I9sSM7A9FPaU8x7lZ12e1TIyxI02U/WysrQC9HIBhj
kzbKTE+4GOfnrCbg1Hv2BkO/r+k5CzXDSceEVeTNFnjryFm4hHM9LgBlsaCMvIb1
DN5NkkdJbD8YLzQKf+MCAwEAAaNhMF8wDgYDVR0PAQH/BAQDAgKkMB0GA1UdJQQW
MBQGCCsGAQUFBwMCBggrBgEFBQcDATAPBgNVHRMBAf8EBTADAQH/MB0GA1UdDgQW
BBSG7Kkmi3mU0yCOmGscvMp68m5sbjANBgkqhkiG9w0BAQsFAAOCAQEAYAmujXhH
1o2fibr6xDaHMSRet5oTHPPLmmByvA6lK5hXymWjgRFUR+kMf9lLMxJmqN/KSj+w
WO/8UjdPgB062jjzYiGZL4D8TBUip15M7fMEcR0A2Ai4t/P2GBNbzYBDWroW9B0u
bwITUnhz3LtraIBdiV52LkrAqwAcNBTRW/0i4uaGPOCIu7hOfXIlCYByjMuUoWnA
WmYPmk4vrDIjf3NhR3vKuZcreleUTK5nozI+twYpw21dKEprtyVEgVDBF7Kdoy5N
M7NKDIvSPI4u2Xc4AgKkZ7KY9/qQa9L6bsrJY0UjTbeNGjikkxlU0031LAM1IFaq
0n8ifeL55X0hdA==
-----END CERTIFICATE-----