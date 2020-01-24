def call(def branch) {
    deleteDir()
    checkout([
            $class: 'GitSCM',
            branches: [[name: "*/${branch}"]],
            extensions: scm.extensions + [[$class: 'CleanCheckout']],
            userRemoteConfigs: scm.userRemoteConfigs
            ])
}