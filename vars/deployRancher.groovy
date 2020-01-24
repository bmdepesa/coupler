def call(Map params) {
    // params.installType = [ "HA", "SN"]
    // params.certs = [ "selfsigned", "letsencrypt", "provided"]
    // params.containerName = <string>
    // params.reportName = <string>
    
    def random = Math.abs(new Random().nextInt() % 1000 + 1)
    def containerName = "${env.JOB_NAME}${env.BUILD_NUMBER}-deploy-${random}"
    def imageName = "rancher-validation-${env.JOB_NAME}${env.BUILD_NUMBER}"

    try {
        def deployTest = ""
        if ("HA" == params.installType) {
            if ( "selfsigned" == params.certs) {
                deployTest = "test_create_selfsigned_ha"
            } else if ("letsencrypt" == params.certs) {
                deployTest = "test_create_letsencrypt_ha"
            } else if ("provided" == param.certs) {
                deployTest = "test_create_provided_certs_ha"
            } else {
                echo "Cert type not provided or not found!"
            }
        } else if ("SN" == params.installType) {
            if ( "selfsigned" == params.certs) {
                deployTest = "test_deploy_rancher_server"
            } else if ("letsencrypt" == params.certs) {
                // tbd
            } else if ("provided" == param.certs) {
                // tbd
            } else {
                echo "Cert type not provided or not found!"
            }
        } else {
            echo "Not a valid install type"
        }

        if (deployTest == "") {
            error("Unable to find a valid deployment configuration!")
        }

        sh "docker run --name ${containerName} -t --env-file .env " +
            "${imageName} /bin/bash -c \'" +
            "pytest -v -s --junit-xml=reports/${params.reportName}.xml --html=reports/${params.reportName}.html " +
            "-k ${deployTest} tests/v3_api/\'"
    } catch(err) {
        echo "Error deploying Rancher!"
        echo err
    }

    try {
        sh "docker cp ${containerName}:/src/rancher-validation/tests/v3_api/resource/out/ ."
        if ("HA") == params.installType) {
            archiveArtifacts "out/kube_config_*"
        }

        sh "docker cp ${containerName}:/src/rancher-validation/tests/v3_api/rancher_env.config ."
        load "rancher_env.config"

        collectReports(containerName)
    } catch (err) {
        echo "Error archiving resources"
    }

    return containerName
}