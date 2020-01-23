/* collect reports from the specified container*/

def call(def containerName) {
    sh "docker cp $containerName:$/src/rancher-validation/reports/ ."   
}