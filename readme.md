# Jenkins Shared Library for Rancher validation tests

This library is specifically for Rancher 2.x Jenkins pipelines, but does contain some general purpose steps & utilities. It's primary purpose is to consolidate common code across pipelines, and make writing complex pipelines faster and easier. Some examples include:

```groovy
// build the test container (which also writes any keyfiles necessary)
stage('Build') {
    buildTestContainer()
}

// deploying Rancher
stage('Deploy HA Rancher server') {
    containers << deployRancher([ installType : 'HA',
                                  reportName : 'ha-deploy',
                                  certs : 'selfsigned',
                                  version : version ])
}

// publishing reports (which are automatically collected in these custom steps)
stage('Publish Reports') {
    publishReports("HA Cluster Matrix Certification")
}
```

## Usage

At the beginning of the pipeline, import the library with:

```groovy
@Library('coupler@master') _
```

Call the custom steps!

```groovy
// Jenkinsfile
node {
    wrapWithColor { // custom step from the shared library
        stage('Stage') {
            sh "ls"
        }
    }
}
```

## Best practices

* Add comments to your steps, at a minimum containing inputs, outputs, and high level purpose
* Don't define entire stages in a step
* Steps that spawn a docker container (docker run) must return the container name, make sure to add a unique identifer to container names from steps that may run multiple times
* When writing a closure step, explicitly define the argument as `Closure body` and call `body()` in the step:

```groovy
// wrapWithColor.groovy
def call(Closure body) {
    wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm', 'defaultFg': 2, 'defaultBg':1]) {
        body()
    }
}
```

## Gotchas

* Most steps assume you are running from dir('tests/validation/') in the Jenkins workspace

## Code Style

* Reuse code wherever possible
* Braces start on the same line as the preceding statment, and end on their own line (unless in a single line statement)
* Use groovy collections / iterators / ranges where possible
