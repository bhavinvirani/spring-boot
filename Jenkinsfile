pipeline {
    agent { label 'agent1' }

    environment {
        PROJECT_NAME = 'spring-boot'
        TAG = 'latest'
        GITHUB_REPO_URL = 'https://github.com/bhavinvirani/spring-boot.git'
    }

    tools {
        maven 'Jenkins-Maven'
    }

    stages {
        stage('Checkout') {
            steps {
                cleanWs()
                sh "git clone https://github.com/bhavinvirani/spring-boot.git"

            }
        }

        stage('Build') {
            steps {
                dir('spring-boot') {
                    sh 'mvn clean package -DskipTests'
                }
            }
            post {
                success {
                    junit 'spring-boot/**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'spring-boot/target/*.jar'
                }
            }
        }

        stage('Test') {
            steps {
                dir('spring-boot') {
                    sh 'mvn test'
                }
            }
            post {
                success {
                    junit 'spring-boot/**/target/surefire-reports/TEST-*.xml'
                }
                failure {
                    junit 'spring-boot/**/target/surefire-reports/TEST-*.xml'
                    script {
                        currentBuild.result = 'UNSTABLE'
                    }
                }
                always {
                    archiveArtifacts 'spring-boot/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Push on Docker Hub') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-cred', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                    sh """
                        cd spring-boot
                        echo "${DOCKERHUB_PASSWORD}" | docker login -u "${DOCKERHUB_USERNAME}" --password-stdin
                        docker build -t ${DOCKERHUB_USERNAME}/${PROJECT_NAME}:${TAG} .
                        docker push ${DOCKERHUB_USERNAME}/${PROJECT_NAME}:${TAG}
                    """
                }
            }
        }
    }
}