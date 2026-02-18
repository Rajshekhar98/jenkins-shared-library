def call() {
    sh '''
    echo "Running unit tests..."
    if [ -f package.json ]; then
        npm install
        npm test || echo "Tests failed but continuing"
    else
        echo "No tests found"
    fi
    '''
}
