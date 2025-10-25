pipeline{
    agent{
        label ''
    }
    tools{
        maven 'M3'
    }
    stages{
        stage('checkout'){
            steps{
                git branch:'master', url:'https://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@github.com/mariemsouadi123/SpringPetClinic.git'
            }
        }
        stage('Build'){
            steps{
                bat 'mvn compile'
            }
        }
        stage('Test'){
            steps{
                bat 'mvn test'
            }
        }
        stage('Package'){
            steps{
                bat 'mvn package'
            }
        }
        stage('Deploy'){
            steps{
                bat 'java -jar target//spring-petclinic-2.1.0.BUILD-SNAPSHOT.jar'
            }
        }
    }
}
