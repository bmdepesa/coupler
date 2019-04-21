def call(Map params) {
    echo "Removing all images"
    sh "docker rmi rancher-validation-${env.JOB_NAME}${env.BUILD_NUMBER}"
}