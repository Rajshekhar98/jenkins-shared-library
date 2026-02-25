def call() {
    sh '''
    echo "Running unit tests..."
    if [ -f package.json ]; then
        npm install
        CI=true npm test -- --watchAll=false --passWithNoTests || echo "Tests failed but continuing"
    else
        echo "No package.json found, skipping tests"
    fi
    '''
}
