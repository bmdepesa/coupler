def lastBuildResult() {
    def previous_build = currentBuild.getPreviousBuild()
    if ( null != previous_build ) { return previous_build.result } else { return 'UNKNOWN' }
}

def call(def env) {
    def String rancher_version_regex = "^v[\\d]\\.[\\d]\\.[\\d][\\-rc\\d]+\$"

    try {
      if (!(DOCKER_TRIGGER_TAG ==~ rancher_version_regex)) {
        println("Received RANCHER_VERSION \'${DOCKER_TRIGGER_TAG}\' via webhook which does not match regex \'${rancher_version_regex}\'.")
        println("** This will **not** result in a pipeline run.")
        currentBuild.result = 'SUCCESS'
      }
    } catch(MissingPropertyException e) {
      // Build did not run from Docker webhook if DOCKER_TRIGGER_TAG doesn't exist
    }
}