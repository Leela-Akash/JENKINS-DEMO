pipeline {
    agent any

    stages {

        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    bat 'npm install'
                    bat 'npm run build'
                }
            }
        }

        stage('Integrate Frontend') {
            steps {
                bat '''
                if exist "Backend\\demo\\src\\main\\resources\\static" rmdir /S /Q "Backend\\demo\\src\\main\\resources\\static"
                mkdir "Backend\\demo\\src\\main\\resources\\static"
                xcopy /E /I /Y frontend\\dist\\* Backend\\demo\\src\\main\\resources\\static\\
                '''
            }
        }

        stage('Build Backend WAR') {
            steps {
                dir('Backend/demo') {
                    bat 'mvn clean package'
                }
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                bat '''
                REM Stop Tomcat
                "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\bin\\shutdown.bat"
                
                REM Remove old WAR and exploded folder
                rmdir /S /Q "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\demo"
                del /Q "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\demo.war"

                REM Copy new WAR
                copy Backend\\demo\\target\\demo.war "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\"

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
            echo 'Build or deployment failed.'
        }
    }
}
