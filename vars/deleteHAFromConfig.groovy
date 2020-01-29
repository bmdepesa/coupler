def call(Map params) {
    def random = Math.abs(new Random().nextInt() % 1000 + 1)
    def containerName = "${env.JOB_NAME}${env.BUILD_NUMBER}-delete-${random}"
    try {
        // sh "docker cp out/. ${containerName}:/src/rancher-validation/tests/v3_api/resource/"
        sh "docker run --name ${containerName} -t --env-file .env " +
            "-v \$(pwd)/out:/src/rancher-validation/tests/v3_api/resource/out " +
            "${imageName} /bin/bash -c \'" +
            "pytest -v -s --junit-xml=delete_ha.xml --html=reports/delete_ha.html " +
            "-k test_delete_ha_from_config tests/v3_api/\'"
    } catch(err) {
        echo "Unable to delete HA installation -- does config file exist?"
        echo "Error: " + err.message
    }
    return containerName
}