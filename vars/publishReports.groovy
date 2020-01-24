/* publish the html and xml reports */

def call(def reportName) {
    stage('Test Report') {
        dir('.') {
            def reportFilesString = sh(script: 'find reports -name "*.html"', returnStdout: true).split().join(',')
            echo "reportFiles: " + reportFilesString

            publishHTML (target: [
                                    allowMissing: false,
                                    alwaysLinkToLastBuild: true,
                                    keepAll: true,
                                    reportDir: ".",
                                    reportFiles: reportFilesString,
                                    reportName: reportName
                                ])
            // Archive all test reports (currently combines all into one report)
            step([$class: 'JUnitResultArchiver', testResults: "**/*.xml"])
        }
    }
}