def call(Map config = [:]) {
    def imageName = config.imageName ?: 'rajshekhar98/easyshop-app'
    def imageTag  = config.imageTag  ?: env.DOCKER_IMAGE_TAG

    sh """
    echo "Running Trivy scan..."
    if ! command -v trivy >/dev/null 2>&1; then
        echo "Installing Trivy..."
        sudo apt-get update -y
        sudo apt-get install -y wget apt-transport-https gnupg lsb-release
        wget -qO - https://aquasecurity.github.io/trivy-repo/deb/public.key | sudo apt-key add -
        echo "deb https://aquasecurity.github.io/trivy-repo/deb \$(lsb_release -sc) main" | sudo tee /etc/apt/sources.list.d/trivy.list
        sudo apt-get update -y
        sudo apt-get install -y trivy
    fi
    trivy image --exit-code 0 --severity HIGH,CRITICAL ${imageName}:${imageTag}
    """
}
