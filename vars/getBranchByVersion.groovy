def call(String version, Map params) {
    def branch = "v2.1"

    if (version.startsWith("v2.2") || version.startsWith("v2.3") || 
        version.startsWith("v2.4") || version == "master-head") {
        branch = "master"
    }

    try { branch = params.BRANCH }
    catch(MissingPropertyException e) {}
    
    return branch
}