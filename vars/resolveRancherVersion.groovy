def call(Map params) {
    try { return DOCKER_TRIGGER_TAG }
    catch(MissingPropertyException e) {}

    if ('' != params.RANCHER_CHART_VERSION && params.RANCHER_CHART_VERSION != null) { return params.RANCHER_CHART_VERSION }
    if ('' != params.RANCHER_SERVER_VERSION && params.RANCHER_SERVER_VERSION != null) { return params.RANCHER_SERVER_VERSION }
    if ('' != params.RANCHER_VERSION && params.RANCHER_VERSION != null) { return params.RANCHER_VERSION }
}