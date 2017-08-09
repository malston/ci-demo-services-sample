#!/bin/bash

mkdir -p ${HOME}/.m2

M2_LOCAL_REPO="${ROOT_FOLDER}/.m2"

mkdir -p ${M2_LOCAL_REPO}

cat > ${HOME}/.m2/settings.xml <<EOF

<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                          https://maven.apache.org/xsd/settings-1.0.0.xsd">
      <localRepository>${M2_LOCAL_REPO}</localRepository>
      <servers>
        <server>
          <id>${M2_SETTINGS_REPO_ID}</id>
          <username>${M2_SETTINGS_REPO_USERNAME}</username>
          <password>${M2_SETTINGS_REPO_PASSWORD}</password>
        </server>
      </servers>
</settings>

EOF
echo "Settings xml written"

export GRADLE_USER_HOME="${ROOT_FOLDER}/.gradle"
mkdir -p ${GRADLE_USER_HOME}

echo "Writing gradle.properties to [${GRADLE_USER_HOME}/gradle.properties]"

cat > ${GRADLE_USER_HOME}/gradle.properties <<EOF

repoUsername=${M2_SETTINGS_REPO_USERNAME}
repoPassword=${M2_SETTINGS_REPO_PASSWORD}

EOF
echo "gradle.properties written"
