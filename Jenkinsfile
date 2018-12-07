pipeline {
    agent any
    tools {
        maven 'Apache Maven 3.5.2'
    }
    stages{
        stage('Checkout') {
            git credentialsId: '3efc89a8-b810-476d-b958-0ca2e68d1b49', url: 'https://github.com/vyjorg/LPDM-Order.git'
        }
        stage('Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
    }
}
