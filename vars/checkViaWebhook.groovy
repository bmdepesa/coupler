def lastBuildResult() {
    def previous_build = currentBuild.getPreviousBuild()
    if ( null != previous_build ) { return previous_build.result } else { return 'UNKNOWN' }
}

def via_webhook(env) {
    try {
        if (env.DOCKER_TRIGGER_TAG != "") {
            return true
        }
    } catch(MissingPropertyException) {
        return false
    }
    return false
}

def call(def env) {
    echo "Env: " + env
    def rancher_version_in = ""

    try { if ('' != env.RANCHER_VERSION) { rancher_version_in = env.RANCHER_VERSION } }
    catch (MissingPropertyException e) {}

    try { rancher_version_in = env.DOCKER_TRIGGER_TAG }
    catch (MissingPropertyException e) {}

    try { if ('' != env.RANCHER_SERVER_VERSION) { rancher_version_in = env.RANCHER_SERVER_VERSION } }
    catch (MissingPropertyException e) {}

    try { if ('' != env.RANCHER_IMAGE_TAG) { rancher_version_in = env.RANCHER_IMAGE_TAG } }
    catch (MissingPropertyException e) {}

    try { if ('' != env.RANCHER_CHART_VERSION) { rancher_version_in = "v" + env.RANCHER_VERSION } }
    catch (MissingPropertyException e) {}
    
    def String rancher_version_regex = "^v[\\d]\\.[\\d]\\.[\\d][\\-rc\\d]+\$"

    // RANCHER_VERSION resolution is first via Jenkins Build Parameter RANCHER_VERSION fed in from console,
    // then from $DOCKER_TRIGGER_TAG which is sourced from the Docker Hub Jenkins plugin webhook.

    if ( true == via_webhook(env) && (!(rancher_version_in ==~ rancher_version_regex)) ) {
        println("Received RANCHER_VERSION \'${rancher_version_in}\' via webhook which does not match regex \'${rancher_version_regex}\'.")
        println("** This will **not** result in a pipeline run.")
        currentBuild.result = lastBuildResult()
        error()
    }

    return rancher_version_in
}