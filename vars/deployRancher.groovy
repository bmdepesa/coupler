def call(Map params) {
    // params.installType = [ "HA", "SN"]
    // params.certs = [ "selfsigned", "letsencrypt", "provided"]
    // params.containerName = <string>
    // params.reportName = <string>
    
    def containerName = "${env.JOB_NAME}${env.BUILD_NUMBER}-deploy"
    def imageName = "rancher-validation-${env.JOB_NAME}${env.BUILD_NUMBER}"

    if ("HA" == params.installType) {
        def deployTest = "test_create_ha"

        if ( "selfsigned" == params.certs) {
            // already matched
        } else if ("letsencrypt" == params.certs) {
            deployTest = "test_create_letsencrypt_ha"
        } else if ("provided" == param.certs) {
            deployTest = "test_create_provided_certs_ha"
        } else {
            echo "Cert type not provided or not found -- defaulting to self signed."
        }

        sh "docker run --name ${containerName} -t --env-file .env " +
            "${imageName} /bin/bash -c \'" +
            "pytest -v -s --junit-xml=${params.reportName}.xml --html=reports/${params.reportName}.html " +
            "-k ${deployTest} tests/v3_api/\'"
        
        sh "docker cp $containerName:/src/rancher-validation/tests/v3_api/resource/kube_config_cluster-ha-filled.yml ."
        archiveArtifacts "kube_config_cluster-ha-filled.yml"
        
    } else if ("SN" == params.installType) {
        sh "docker run --name ${containerName} -t --env-file .env " +
            "${imageName} /bin/bash -c \'" +
            "pytest -v -s --junit-xml=${params.reportName}.xml --html=reports/${params.reportName}.html" +
            "-k test_deploy_rancher_server tests/v3_api/\'"
    } else {
        // not a valid install type
        echo "Not a valid install type"
    }

    sh "docker cp ${containerName}:/src/rancher-validation/tests/v3_api/rancher_env.config ."
    load "rancher_env.config"

    collectReports(containerName)

    return containerName
}