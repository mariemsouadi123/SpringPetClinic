pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "mariemsouadi12189/spring-petclinic"
        DOCKER_TAG = "latest"
        DOCKER_CREDS = credentials('dockerhub-creds')

        KUBE_NAMESPACE = "default"
        KUBE_DEPLOYMENT = "spring-petclinic"
    }

    stages {

        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '''
                    mvn sonar:sonar \
                    -Dsonar.projectKey=springpetclinic \
                    -Dsonar.projectName=springpetclinic \
                    -Dsonar.java.binaries=target
                    '''
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE:$DOCKER_TAG .'
            }
        }

        stage('Login to Docker Hub') {
            steps {
                sh 'echo $DOCKER_CREDS_PSW | docker login -u $DOCKER_CREDS_USR --password-stdin'
            }
        }

        stage('Push Docker Image') {
            steps {
                sh 'docker push $DOCKER_IMAGE:$DOCKER_TAG'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                kubectl apply -f k8s/deployment.yaml
                kubectl apply -f k8s/service.yaml
                kubectl rollout status deployment/$KUBE_DEPLOYMENT -n $KUBE_NAMESPACE
                '''
            }
        }
    }

    post {

        success {
            mail to: 'souadimariem74@gmail.com',
                 subject: " Jenkins Pipeline SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: """
Hello Mariem,

Good news 🎉

Your Jenkins pipeline has completed SUCCESSFULLY.

Job Name   : ${env.JOB_NAME}
Build No   : ${env.BUILD_NUMBER}
Build URL  : ${env.BUILD_URL}

The application has been:
- Built successfully
- Analyzed by SonarQube
- Docker image pushed to Docker Hub
- Deployed to Kubernetes

You can now access the application via the Kubernetes service.

Regards,
Jenkins
"""
        }

        failure {
            mail to: 'souadimariem74@gmail.com',
                 subject: " Jenkins Pipeline FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: """
Hello Mariem,

The Jenkins pipeline has FAILED 

Job Name   : ${env.JOB_NAME}
Build No   : ${env.BUILD_NUMBER}
Build URL  : ${env.BUILD_URL}

Please check the Jenkins logs for more details.

Regards,
Jenkins
"""
        }
    }
}

