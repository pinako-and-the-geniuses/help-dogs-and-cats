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

        stage('Backend Build') {
            steps {
                sh 'chmod u+x ./backend/gradlew'
                sh './backend/gradlew build'
                sh 'docker build -t backend ./backend/'
            }
        }
        
        stage('Frontend Deploy') {
            steps {
                sh 'docker ps -q --filter name=frontend | grep -q . && docker stop frontend && docker rm frontend'
                sh 'docker run -d --name frontend -p 80:80 -p 443:443 frontend'
            }
        }

        stage('Backend Deploy') {
            steps {
                sh 'docker ps -q --filter name=backend | grep -q . && docker stop backend && docker rm backend'
                sh 'docker run -d --name backend -p 8080:8080 backend'
            }
        }

        stage('Finish') {
            steps{
                sh 'docker images -qf dangling=true | xargs -I{} docker rmi {}'
            }
        }
    }
}
