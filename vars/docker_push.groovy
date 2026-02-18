def call(Map config) {
    withCredentials([usernamePassword(
        credentialsId: config.credentials,
        usernameVariable: 'DOCKER_USER',
        passwordVariable: 'DOCKER_PASS'
    )]) {
        sh """
        echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin
        docker push ${config.imageName}:${config.imageTag}
        """
    }
}
