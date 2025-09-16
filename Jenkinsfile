pipeline {
    agent any

    stages {

        // ===== FRONTEND BUILD =====
        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    bat 'npm install'
                    bat 'npm run build'
                }
            }
        }

        // ===== COPY FRONTEND TO BACKEND =====
        stage('Integrate Frontend into Backend') {
            steps {
                // Copy frontend build into Spring Boot static folder
                bat '''
                if exist "Backend\\demo\\src\\main\\resources\\static" (
                    rmdir /S /Q "Backend\\demo\\src\\main\\resources\\static"
                )
                mkdir "Backend\\demo\\src\\main\\resources\\static"
                xcopy /E /I /Y frontend\\dist\\* Backend\\demo\\src\\main\\resources\\static\\
                '''
            }
        }

        // ===== BACKEND BUILD =====
        stage('Build Backend') {
            steps {
                dir('Backend/demo') { 
                    bat 'mvn clean package'
                }
            }
        }

        // ===== DEPLOY BACKEND (with frontend included) =====
        stage('Deploy Backend to Tomcat') {
            steps {
                bat '''
                if exist "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\ROOT.war" (
                    del /Q "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\ROOT.war"
                )
                if exist "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\ROOT" (
                    rmdir /S /Q "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\ROOT"
                )
                copy "Backend\\demo\\target\\*.war" "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\ROOT.war"
                '''
            }
        }

    }

    post {
        success {
            echo 'Deployment Successful! Frontend and Backend are now served together.'
        }
        failure {
            echo 'Pipeline Failed.'
        }
    }
}
