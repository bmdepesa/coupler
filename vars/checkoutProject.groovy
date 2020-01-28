def call(def branch) {
    checkout([
            $class: 'GitSCM',
            branches: [[name: "*/${branch}"]],
            extensions: scm.extensions + [[$class: 'CleanCheckout']],
            userRemoteConfigs: scm.userRemoteConfigs
            ])
}