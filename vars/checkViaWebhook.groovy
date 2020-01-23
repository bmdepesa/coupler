def rancher_version() {
        try { if ('' != RANCHER_VERSION) { return RANCHER_VERSION } }
        catch (MissingPropertyException e) {}

        try { return DOCKER_TRIGGER_TAG }
        catch (MissingPropertyException e) {}

        echo  'Neither RANCHER_VERSION nor DOCKER_TRIGGER_TAG have been specified!'
        //error()
    }

def lastBuildResult() {
    def previous_build = currentBuild.getPreviousBuild()
    if ( null != previous_build ) { return previous_build.result } else { return 'UNKNOWN' }
}

def via_webhook() {
    try {
        def foo = DOCKER_TRIGGER_TAG
        return true
    } catch(MissingPropertyException) {
        return false
    }
}

def call(Map params) {
    
    def rancher_version_in = rancher_version()
    def String rancher_version_regex = "^v[\\d]\\.[\\d]\\.[\\d][\\-rc\\d]+\$"

    // RANCHER_VERSION resolution is first via Jenkins Build Parameter RANCHER_VERSION fed in from console,
    // then from $DOCKER_TRIGGER_TAG which is sourced from the Docker Hub Jenkins plugin webhook.

    if ( true == via_webhook() && (!(rancher_version_in ==~ rancher_version_regex)) ) {
        println("Received RANCHER_VERSION \'${rancher_version_in}\' via webhook which does not match regex \'${rancher_version_regex}\'.")
        println("** This will **not** result in a pipeline run.")
        currentBuild.result = lastBuildResult()
        error()
    }
    return rancher_version_in
}