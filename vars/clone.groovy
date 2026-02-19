def call(String repoUrl, String branchName, String credentialsId = 'github-cred') {
    git branch: branchName,
        url: repoUrl,
        credentialsId: credentialsId
}
