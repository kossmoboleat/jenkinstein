#!/usr/bin/env bash

#mvn install:install-file -Dfile=lib/voice-dfki-spike-5.1.jar -DartifactId=voice-dfki-spike -Dversion=5.1 -DgroupId=local -Dpackaging=jar -DlocalRepositoryPath=repo -DpomFile=lib/pom.xml

mvn deploy:deploy-file -Dfile=lib/voice-dfki-spike-5.1.jar -DartifactId=voice-dfki-spike -Dversion=5.1 -DgroupId=local -Dpackaging=jar -Durl=file:repo -DpomFile=lib/pom.xml