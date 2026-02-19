#!/usr/bin/env groovy

/**
 * Update Kubernetes manifests with new image tags
 */

def call(Map config = [:]) {

    def imageTag       = config.imageTag ?: error("Image tag is required")
    def manifestsPath  = config.manifestsPath ?: 'kubernetes'
    def gitCredentials = config.gitCredentials ?: 'github-cred'
    def gitUserName    = config.gitUserName ?: 'Jenkins CI'
    def gitUserEmail   = config.gitUserEmail ?: 'jenkins@example.com'
    def gitBranch      = config.gitBranch ?: 'main'

    echo "Updating Kubernetes manifests with image tag: ${imageTag}"

    withCredentials([
        usernamePassword(
            credentialsId: gitCredentials,
            usernameVariable: 'GIT_USERNAME',
            passwordVariable: 'GIT_PASSWORD'
        )
    ]) {

        sh """
        git config user.name "${gitUserName}"
        git config user.email "${gitUserEmail}"
        """

        sh """
        # Update main application image
        sed -i "s|image: rajshekhar98/easyshop-app:.*|image: rajshekhar98/easyshop-app:${imageTag}|g" \
        ${manifestsPath}/08-easyshop-deployment.yaml

        # Update migration job image (if exists)
        if [ -f "${manifestsPath}/12-migration-job.yaml" ]; then
          sed -i "s|image: rajshekhar98/easyshop-migration:.*|image: rajshekhar98/easyshop-migration:${imageTag}|g" \
          ${manifestsPath}/12-migration-job.yaml
        fi

        # Ensure ingress domain
        if [ -f "${manifestsPath}/10-ingress.yaml" ]; then
          sed -i "s|host: .*|host: easyshop.letsdeployit.com|g" \
          ${manifestsPath}/10-ingress.yaml
        fi
        """

        sh """
        if git diff --quiet; then
          echo "No Kubernetes manifest changes detected"
        else
          git add ${manifestsPath}
          git commit -m "Update Kubernetes image tag to ${imageTag}"
          git push https://\$GIT_USERNAME:\$GIT_PASSWORD@github.com/Rajshekhar98/devOps-project_thynk-tech.git HEAD:${gitBranch}
        fi
        """
    }
}

