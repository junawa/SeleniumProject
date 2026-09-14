pipeline {
    agent any

    parameters {
        choice(name: 'SUITE', choices: ['bvt', 'smoke', 'regression', 'e2e', 'api'], description: 'TestNG suite')
        choice(name: 'BROWSER', choices: ['chrome', 'edge'], description: 'Browser')
        choice(name: 'ENVIRONMENT', choices: ['local', 'qa', 'staging'], description: 'Target environment')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Run Chrome headlessly')
    }

    stages {
        stage('Test') {
            steps {
                bat "mvn --batch-mode clean test -Dsuite=${params.SUITE} -Dbrowser=${params.BROWSER} -Denv=${params.ENVIRONMENT} -Dheadless=${params.HEADLESS}"
            }
        }
        stage('Allure report') {
            steps {
                bat 'mvn allure:report'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/testng-results.xml'
            archiveArtifacts allowEmptyArchive: true, artifacts: 'target/allure-results/**,target/site/allure-maven/**'
            publishHTML(target: [
                reportDir: 'target/site/allure-maven',
                reportFiles: 'index.html',
                reportName: 'Allure Report',
                keepAll: true
            ])
        }
    }
}
