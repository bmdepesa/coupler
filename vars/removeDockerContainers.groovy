def call(List containers) {
    echo "Removing all containers" 
    containers.each { 
        echo "Removing ${it}"
        sh "docker stop ${it} && docker rm -v ${it}"
    }
}