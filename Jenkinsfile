pipeline {
    agent any
    tools {
        maven 'Apache Maven 3.5.2'
    }
    stages{
        stage('Checkout') {
            steps {
                git 'https://github.com/vyjorg/LPDM-Order'
            }
        }
        stage('Tests') {
            steps {
                sh 'mvn clean test'
            }
            post {
                always {
                    junit 'target/surefire-reports/**/*.xml'
                }
                failure {
                    error 'The tests failed'
                }
            }
        }
        stage('Push to DockerHub') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Deploy') {
            steps {
                sh 'docker stop LPDM-OrderMS || true && docker rm LPDM-OrderMS || true'
                sh 'docker pull vyjorg/lpdm-order:latest'
                sh 'docker run -d --name LPDM-OrderMS -p 28083:28083 --link LPDM-OrderDB --restart always --memory-swappiness=0  -e "JAVA_TOOL_OPTIONS=-Djasypt.encryptor.password==<!+&<" vyjorg/lpdm-order:latest'
            }
        }
    }
}
