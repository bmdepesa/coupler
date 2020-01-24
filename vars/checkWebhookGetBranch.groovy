def lastBuildResult() {
    def previous_build = currentBuild.getPreviousBuild()
    if ( null != previous_build ) { return previous_build.result } else { return 'UNKNOWN' }
}

def via_webhook(env) {
    if (env.DOCKER_TRIGGER_TAG != "" && env.DOCKER_TRIGGER_TAG != null) {
        return true
    }
    return false
}

def call(def env) {
    def rancher_version_in = ""

    if ('' != env.RANCHER_VERSION && env.RANCHER_VERSION != null) { rancher_version_in = env.RANCHER_VERSION }
    if ('' != env.RANCHER_SERVER_VERSION && env.RANCHER_SERVER_VERSION != null) { rancher_version_in = env.RANCHER_SERVER_VERSION }
    if ('' != env.RANCHER_CHART_VERSION && env.RANCHER_CHART_VERSION != null) { rancher_version_in = "v" + env.RANCHER_CHART_VERSION }
    if ('' != env.RANCHER_IMAGE_TAG && env.RANCHER_IMAGE_TAG != null) { rancher_version_in = env.RANCHER_IMAGE_TAG }
    if ('' != env.DOCKER_TRIGGER_TAG && env.DOCKER_TRIGGER_TAG != null) { rancher_version_in = env.DOCKER_TRIGGER_TAG }
    
    def String rancher_version_regex = "^v[\\d]\\.[\\d]\\.[\\d][\\-rc\\d]+\$"

    if ( true == via_webhook(env) && (!(rancher_version_in ==~ rancher_version_regex)) ) {
        println("Received RANCHER_VERSION \'${rancher_version_in}\' via webhook which does not match regex \'${rancher_version_regex}\'.")
        println("** This will **not** result in a pipeline run.")
        currentBuild.result = lastBuildResult()
        error()
    }

    def branch = "v2.1"
    if (rancher_version_in.startsWith("v2.2") || rancher_version_in.startsWith("v2.3") || rancher_version_in == "master-head") {
        branch = "master"
    }
    if (env.BRANCH != "" && env.Branch != null) {
        branch = "${env.BRANCH}"
    }
}