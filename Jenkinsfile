#!/usr/bin/env groovy

def jenkinsfile
def version='v3.1.1'
fileLoader.withGit('https://git.aurora.skead.no/scm/ao/aurora-pipeline-scripts.git', version) {
   jenkinsfile = fileLoader.load('templates/leveransepakke')
}

def overrides = [piTests: false]
jenkinsfile.gradle(version, overrides)