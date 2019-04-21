def call(def containers) {
    echo "Removing all containers" 
    containers.each { containerName ->
        sh "docker stop ${containerName}"
        sh "docker rm ${containerName}"
    }
}