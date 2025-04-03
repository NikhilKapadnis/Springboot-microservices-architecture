pipeline {
    agent any

    environment {
        AWS_ACCOUNT_ID = "430803014839"
        AWS_DEFAULT_REGION = "eu-north-1"
        IMAGE_REPO_NAME = "wineapp-springboot"
        IMAGE_TAG = "latest"
        REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"
        CONTAINER_NAME = "wineapp"
        PORT = "8081"
        NETWORK="winenetwork"
        IMG_ID=''

        // SonarQube details
        SONARQUBE_URL = "http://13.48.119.95:9000"
        SONARQUBE_TOKEN = "squ_440c15e7427ea888f6fb2c70c4d7e4bb63623867" 
    }

    tools {
        maven 'maven-3.9.9' 
    }

    stages {

        stage('Logging into AWS ECR') {
            steps {
                script {
                    sh "aws ecr get-login-password --region ${AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com"
                }
            }
        }

        stage('Cloning Git') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], 
                extensions: [], 
                userRemoteConfigs: [[credentialsId: 'github-creds', url: 'https://github.com/NikhilKapadnis/tus-wineapp.git']])
            }
        }

        stage('Code Analysis with SonarQube') {
            steps {
                script {
                    sh '''
                        mvn clean verify sonar:sonar \
                          -Dsonar.projectKey=springboot-app \
                          -Dsonar.host.url=${SONARQUBE_URL} \
                          -Dsonar.login=${SONARQUBE_TOKEN} \
                          -Dsonar.java.binaries=target/classes
                    '''
                }
            }
        }

        stage('Run Tests & Generate Coverage Report') {
            steps {
                script {
                    jacoco(
                        execPattern: '**/jacoco.exec',
                        classPattern: '**/classes',
                        sourcePattern: '**/src/main/java',
                        classDirectories: [[pattern: '**/classes']],
                        sourceDirectories: [[pattern: '**/src/main/java']]
                    )
                }
            }
        }


        stage('Building Image') {
            steps {
                script {
                    dockerImage = docker.build "${REPOSITORY_URI}:${IMAGE_TAG}"
                }
            }
        }

        stage('Pushing to ECR') {
            steps {
                script {
                    sh "docker push ${REPOSITORY_URI}:${IMAGE_TAG}"
                }
            }
        }
        
               stage("Logged in into Agent") {
            agent { label 'agent-node' }
            steps {
                script {
                    sh "aws ecr get-login-password --region ${AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com"
                }
            }
        }

        stage('Pull Updated Image') {
            agent { label 'agent-node' }
            steps {
                script {
                    sh "docker pull ${REPOSITORY_URI}:${IMAGE_TAG}"
                }
            }
        }

        stage('Stop Old Container') {
            agent { label 'agent-node' }
            steps {
                script {
                    sh "docker stop ${CONTAINER_NAME} || true"
                }
            }
        }

        stage('Remove Old Container') {
            agent { label 'agent-node' }
            steps {
                script {
                    sh "docker rm ${CONTAINER_NAME} || true"
                }
            }
        }

        stage("Run New container") {
            agent { label 'agent-node' }
            steps {
                script {
                    sh "docker run --name ${CONTAINER_NAME} -d --restart always -p ${PORT}:${PORT}  ${REPOSITORY_URI}:${IMAGE_TAG}"
                }
            }
        }
        
        stage('Prune Dangling Images') {
            agent { label 'agent-node' }
            steps {
                script {
                    sh "docker images --quiet --filter=dangling=true | xargs --no-run-if-empty docker rmi"
                }
            }
        }

    }

    post {
        always {
            script {
                echo "All stages completed successfully!"
            }
        }
    }
}
