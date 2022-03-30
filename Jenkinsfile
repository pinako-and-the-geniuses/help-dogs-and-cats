pipeline {
    agent any
    
    environment {
        GIT_URL = "https://lab.ssafy.com/s06-bigdata-dist-sub2/S06P22A302.git"
    }

    tools {
        nodejs "nodejs"
    }

    stages {
        stage('Frontend Build') {
            steps {
                sh 'docker build -t frontend ./frontend/'
            }
        }

        stage('Frontend Deploy') {
            steps {
                sh 'docker ps -q --filter name=frontend | grep -q . && docker stop frontend && docker rm frontend'
                sh 'docker run -d -p 80:80 -p 443:443 --name frontend frontend'
            }
        }

        stage('Finish') {
            steps{
                sh 'docker images -qf dangling=true | xargs -I{} docker rmi {}'
            }
        }
    }
}
