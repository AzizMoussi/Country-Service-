pipeline {
    agent any

    tools {
        jdk 'JDK21'           
        maven 'Maven-WSL'        
    }

    environment {
        TOMCAT_HOME = "/mnt/c/Program Files/Apache Software Foundation/Tomcat 9.0"
    }


    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'git@github.com:AzizMoussi/Country-Service-'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                script {
                    def warFile = "target/${env.JOB_NAME}.war"
                    sh 'cp target/*.war "/mnt/c/Program Files/Apache Software Foundation/Tomcat 9.0/webapps/"'

                }
            }
        }
    }

}
