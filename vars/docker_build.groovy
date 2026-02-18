def call(Map config) {
    sh """
    docker build -t ${config.imageName}:${config.imageTag} \
    -f ${config.dockerfile} ${config.context}
    """
}
