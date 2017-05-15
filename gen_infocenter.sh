#!/bin/sh

# initiated by Jared on May 9, 2017.
# This script is to create mvn project initially.
# Generate jersey skeleton project for restful service
# The war could be loaded by tomcat

mvn archetype:generate \
   -DgroupId=com.ustudy.infocent \
   -DartifactId=info-center \
   -DarchetypeGroupId=org.glassfish.jersey.archetypes \
   -DarchetypeVersion=2.25.1 \
   -DarchetypeArtifactId=jersey-quickstart-webapp \
   -DinteractiveMode=false

# Noted: To use Eclipse IDE to develop, maybe need to run mvn eclipse:clean eclipse:eclipse
# to generate necessary files.

