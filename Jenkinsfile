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

        // ===== FRONTEND DEPLOY =====
        stage('Deploy Frontend to Tomcat') {
            steps {
                bat '''
                if exist "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\frontend" (
                    rmdir /S /Q "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\frontend"
                )
                mkdir "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\frontend"
                xcopy /E /I /Y frontend\\dist\\* "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\frontend"
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

        // ===== BACKEND DEPLOY =====
        stage('Deploy Backend to Tomcat') {
            steps {
                bat '''
                REM Stop Tomcat if needed
                "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\bin\\shutdown.bat"

                REM Delete old WAR and folder
                if exist "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\demo.war" (
                    del /Q "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\demo.war"
                )
                if exist "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\demo" (
                    rmdir /S /Q "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\demo"
                )

                REM Copy new WAR
                copy "Backend\\demo\\target\\demo-0.0.1-SNAPSHOT.war" "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\demo.war"

                REM Start Tomcat
                "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\bin\\startup.bat"
                '''
            }
        }

    }

    post {
        success {
            echo 'Frontend and Backend built and deployed to Tomcat successfully!'
        }
        failure {
            echo 'Pipeline Failed.'
        }
    }
}
