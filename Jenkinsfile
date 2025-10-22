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
        stage('Compile , test code , package in war file and store in maven repo'){
                steps{
                    sh 'mvn clean install'
                }
                post{
                    success{
                       junit allowEmptyResults: true,testResults : '**/target/surfire-reports'
                    }
                }
        }
        stage{
            steps{
                WithSonarQubeEnv(installationName:'MySonarQubeServer',credentialsIs:'sonarQubePWD'){
                    sh "mvn clean verify sonar:sonar
                          -Dsonar.projectKey=country-servicee
                          -Dsonar.projectName='country-servicee'
                          -Dsonar.host.url=http://localhost:9000
                          -Dsonar.token=sqp_dd76104d0154d1a322fe7a3d3cfc25751d6c79f4"
                }
            }
        }
    }

}
