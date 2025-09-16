pipeline {
    agent any

    environment {
        JAVA_HOME = "C:\\Program Files\\Java\\jdk-21"  // adjust if needed
        PATH = "${JAVA_HOME}\\bin;${env.PATH}"
    }

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

        // ===== INTEGRATE FRONTEND INTO BACKEND =====
        stage('Integrate Frontend') {
            steps {
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
        stage('Build Backend WAR') {
            steps {
                dir('Backend/demo') {
                    bat 'mvn clean package'
                }
            }
        }

        // ===== RUN WAR =====
        stage('Run Application') {
            steps {
                bat '''
                REM Kill existing process on port 8080 (optional)
                for /f "tokens=5" %%a in ('netstat -a -n -o ^| findstr :8080 ^| findstr LISTENING') do taskkill /F /PID %%a

                REM Run the Spring Boot WAR
                start java -jar target\\demo-0.0.1-SNAPSHOT.war
                '''
            }
        }
    }

    post {
        success {
            echo 'Frontend and Backend built and running successfully!'
        }
        failure {
            echo 'Pipeline Failed.'
        }
    }
}
