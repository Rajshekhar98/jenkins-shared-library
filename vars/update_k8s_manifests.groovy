def call(Map config) {
    withCredentials([usernamePassword(
        credentialsId: config.gitCredentials,
        usernameVariable: 'GIT_USER',
        passwordVariable: 'GIT_PASS'
    )]) {
        sh """
        git config user.name "${config.gitUserName}"
        git config user.email "${config.gitUserEmail}"

        sed -i "s/tag:.*/tag: ${config.imageTag}/" ${config.manifestsPath}/**/*.yaml

        git add ${config.manifestsPath}
        git commit -m "Update image tag to ${config.imageTag}" || echo "No changes to commit"

        git push https://\$GIT_USER:\$GIT_PASS@github.com/Rajshekhar98/devOps-project_thynk-tech.git HEAD:main
        """
    }
}
