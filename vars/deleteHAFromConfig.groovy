def call(Map params) {
    def containerName = "${env.JOB_NAME}${env.BUILD_NUMBER}-delete"
    echo "Loading HA config for deletion"
    sh "docker cp tests/validation/ha_delete.config ${containerName}:/src/rancher-validation/tests/v3_api/resource"
    sh "docker run --name ${containerName} -t --env-file .env " +
            "${imageName} /bin/bash -c \'" +
            "pytest -v -s --junit-xml=delete_ha.xml --html=reports/delete_ha.html " +
            "-k test_delete_ha_from_config tests/v3_api/\'"
    return containerName
}