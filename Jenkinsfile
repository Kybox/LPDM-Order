pipeline{

    agent any
    properties([disableConcurrentBuilds()])

    stage('Checkout') {
        git credentialsId: '3efc89a8-b810-476d-b958-0ca2e68d1b49', url: 'https://github.com/vyjorg/LPDM-Order.git'
    }
    stage('Build') {
        steps {
            sh 'mvn -Dmaven.test.failure.ignore=true install'
        }
        post {
            success {
                junit 'target/surefire-reports/**/*.xml'
            }
        }
    }
}