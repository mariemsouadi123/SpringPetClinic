pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "mariemsouadi12189/spring-petclinic"
        DOCKER_TAG = "latest"
        DOCKER_CREDS = credentials('dockerhub-creds')
        KUBE_NAMESPACE = "default"
        KUBE_DEPLOYMENT = "spring-petclinic"
        LOCAL_PORT = "8888"
        POD_PORT = "8085"
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
                script {
                    // Apply deployment & service
                    sh '''
                    kubectl apply -f deployment.yaml
                    kubectl apply -f service.yaml
                    kubectl rollout status deployment/$KUBE_DEPLOYMENT
                    '''

                    // Get pod name and port-forward inside one shell block
                    sh '''
                    POD_NAME=$(kubectl get pods -l app=$KUBE_DEPLOYMENT -o jsonpath="{.items[0].metadata.name}")
                    echo "Port-forwarding pod $POD_NAME to http://localhost:$LOCAL_PORT"
                    kubectl port-forward $POD_NAME $LOCAL_PORT:$POD_PORT &
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully with SonarQube, Docker, and Kubernetes!'
        }
        failure {
            echo 'Pipeline failed'
        }
    }
}

