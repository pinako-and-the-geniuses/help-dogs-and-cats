pipeline {
    agent any
    
    environment {
        GIT_URL = "https://lab.ssafy.com/s06-bigdata-dist-sub2/S06P22A302.git"
    }

    tools {
        nodejs "nodejs"
    }

    stages {
        // stage('Pull') {
        //     steps {
        //         git url: "${GIT_URL}", branch: "develop", poll: true, changelog: true
        //     }
        // }
        
        stage('React Build') {
            steps {
                sh 'npm install -g yarn'
                sh 'yarn --cwd ./client install --network-timeout 100000'
                sh 'yarn --cwd ./client build'
            }
        }
        
        stage('Build') {
            steps {
                sh 'docker build -t frontend ./frontend/'
            }
        }
        
        stage('Deploy') {
            steps{
                sh 'docker ps -q --filter name=frontend | grep -q . && docker stop frontend && docker rm frontend'
                sh 'docker run -d --name frontend -p 80:80 -p 443:443 frontend'
            }
        }

       stage('Finish') {
            steps{
                sh 'docker images -qf dangling=true | xargs -I{} docker rmi {}'
            }
        }
    }
}
