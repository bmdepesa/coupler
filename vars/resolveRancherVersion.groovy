def call(Map params) {
    try { return DOCKER_TRIGGER_TAG }
    catch(MissingPropertyException e) {}

    try { return params.RANCHER_IMAGE_TAG }
    catch(MissingPropertyException e) {}

    try { return "v" + params.RANCHER_CHART_VERSION }
    catch(MissingPropertyException e) {}

    try { return params.RANCHER_SERVER_VERSION }
    catch(MissingPropertyException e) {}

    try { return params.RANCHER_VERSION }
    catch(MissingPropertyException e) {}
}