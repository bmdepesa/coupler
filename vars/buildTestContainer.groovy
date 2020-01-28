/* build the test container */

def call(Map params) {
    echo "Configuring and building test container"
    if (env.AWS_SSH_PEM_KEY && env.AWS_SSH_KEY_NAME) {
        dir(".ssh/") {
            // We store the key in base64 to preserve formatting due to a Jenkins bug
            def decoded = new String(env.AWS_SSH_PEM_KEY.decodeBase64())
            writeFile file: env.AWS_SSH_KEY_NAME, text: decoded
        }
    }
    
    sh "./tests/v3_api/scripts/configure.sh"
    sh "./tests/v3_api/scripts/build.sh"
}