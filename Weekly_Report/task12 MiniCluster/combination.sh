#!/bin/bash

JAVA_HOME="/opt/module/jdk-15.0.2/"
MAVEN_HOME="/opt/module/apache-maven-3.8.2"
export PATH="$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH"

property=$1
newValue=$2
DTest=$3

java BroadCastClient $property $newValue &


pid=$(jps|grep BroadCastClient|awk  '{print $1}')
#kill -9 $pid