def call(List containers) {
    echo "Removing all containers" 
    containers.each { 
        echo "Removing ${it}"
        sh "docker stop ${it} || true && docker rm -v ${it}"
    }
}