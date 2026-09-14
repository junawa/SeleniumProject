pipeline {
    agent any

    triggers {
        // Polls every 5 minutes for new commits on this branch/PR.
        // Only fires builds when it finds changes — doesn't run on a bare schedule.
        pollSCM('H/5 * * * *')
    }

    parameters {
        choice(name: 'SUITE', choices: ['auto', 'bvt', 'smoke', 'regression', 'e2e', 'api'], description: '"auto" = bvt on PRs, smoke on branch pushes')
        choice(name: 'BROWSER', choices: ['chrome', 'edge'], description: 'Browser')
        choice(name: 'ENVIRONMENT', choices: ['local', 'qa', 'staging'], description: 'Target environment')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Run Chrome headlessly')
    }

    stages {
        stage('Resolve Suite') {
            steps {
                script {
                    if (params.SUITE != 'auto') {
                        env.RESOLVED_SUITE = params.SUITE
                    } else if (env.CHANGE_ID) {
                        // env.CHANGE_ID is only set when Multibranch Pipeline is building a PR
                        env.RESOLVED_SUITE = 'bvt'
                    } else {
                        env.RESOLVED_SUITE = 'smoke'
                    }
                    echo "Resolved suite: ${env.RESOLVED_SUITE} (branch: ${env.BRANCH_NAME})"
                }
            }
        }

        stage('Test') {
            steps {
                bat "mvn --batch-mode clean test -Dsuite=${env.RESOLVED_SUITE} -Dbrowser=${params.BROWSER} -Denv=${params.ENVIRONMENT} -Dheadless=${params.HEADLESS}"
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
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            archiveArtifacts allowEmptyArchive: true, artifacts: 'target/allure-results/**,target/site/allure-maven/**'
            publishHTML(target: [
                allowMissing: true,
                reportDir: 'target/site/allure-maven',
                reportFiles: 'index.html',
                reportName: 'Allure Report',
                keepAll: true
            ])
        }
    }
}