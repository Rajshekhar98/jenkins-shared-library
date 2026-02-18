def call(String repoUrl, String branchName) {
    git branch: branchName, url: repoUrl
}

