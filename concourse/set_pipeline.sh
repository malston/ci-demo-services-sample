#!/bin/bash

PIPELINE_NAME=${1:-ci-demo-concourse}
ALIAS=${2:-docker}
CREDENTIALS=${3:-credentials.yml}

echo y | fly -t "${ALIAS}" sp -p "${PIPELINE_NAME}" -c pipeline.yml -l "${CREDENTIALS}"
