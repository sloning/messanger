pipeline {
    agent any

    tools {
        gradle 'Gradle-7.4.2'
    }

    stages {
        stage("Build") {
            steps {
                sh "./gradlew bootJar"
            }
        }
        stage("Build Docker image") {
            steps {
                sh "./gradlew docker"
            }
        }
        stage("Deploy") {
            steps {
                sh "./gradlew dockerRun"
            }
        }
    }
}