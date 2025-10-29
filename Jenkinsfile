pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven-WSL'
    }

    environment {
        TOMCAT_HOME = "/mnt/c/Program Files/Apache Software Foundation/Tomcat 9.0"
        NEXUS_REPO_URL = "http://localhost:8081/repository/mavenreleases/"
        NEXUS_CREDENTIALS = "nexusCreds"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'git@github.com:AzizMoussi/Country-Service-'
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean install'
            }
            post {
                success {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Build Dockerfile'){
            steps{
                sh 'docker build -t mycountryservice:$BUILD_NUMBER '
                withCredentials([string(credentialsId: 'dockerhub-paswd', variable: 'dockerhub-pwd')]) {
                    sh 'docker login -u azizmoussi -p $dockerhub-pwd'
                }
                sh ' docker tag mycountryservice:$BUIL_NUMBER azizmoussi/mycountryservice:$BUILD_NUMBER'
                sh 'docker push azizmoussi/mycountryservice:$BUILD_NUMBER'
            }

        }
        stage('Deploy microservice'){
            steps{
                sh 'docker rm -f $(docker ps -aq)'
                sh ' docker run -d -p 8082:8082 --name  mycountryservice:$BUILD_NUMBER'
            }
        }



/*        stage('Check WAR') {
            steps {
                sh 'ls -l target/'
            }
        }


        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv(installationName: 'MySonarQubeServer', credentialsId: 'sonarqubePWD') {
                    sh """
                        mvn clean verify sonar:sonar \
                            -Dsonar.projectKey=country-servicee \
                            -Dsonar.projectName='country-servicee' \
                            -Dsonar.host.url=http://localhost:9000 \
                            -Dsonar.token=sqp_dd76104d0154d1a322fe7a3d3cfc25751d6c79f4
                    """
                }
            }
        }

        stage('Upload Artifact to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexusCreds', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                    sh """
                        mvn deploy:deploy-file \
                          -DgroupId=com.aziz.country \
                          -DartifactId=country-service \
                          -Dversion=1.0.0 \
                          -Dpackaging=war \
                          -Dfile=target/FirstStep-1.0-SNAPSHOT.war \
                          -DrepositoryId=nexus \
                          -Durl=http://localhost:8081/repository/mavenreleases/
                    """
                }
            }
        }




        stage('Deploy to Tomcat') {
            steps {
                script {
                    def warFile = "target/${env.JOB_NAME}.war"
                    sh 'cp target/*.war "/mnt/c/Program Files/Apache Software Foundation/Tomcat 9.0/webapps/"'
                }
            }
        }*/
    }
}
