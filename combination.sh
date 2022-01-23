#!/bin/bash

JAVA_HOME="/opt/module/jdk-15.0.2/"
MAVEN_HOME="/opt/module/apache-maven-3.8.2"
export PATH="$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH"


# "./combination.sh dfs.encrypt.data.transfer true ALL"
property=$1
newValue=$2
DTest=$3

javac BroadCastClient.java
nohup java BroadCastClient $property $newValue &

if [ "$DTest" == "ALL" ]||[ -z "$DTest" ];then
	mvn test
else
	mvn -Dtest=$DTest test
fi

pid=$(jps | grep BroadCastClient | awk '{print $1}')
kill -9 $pid

python3 analyze/Error_Analyze.py $property $newValue $DTest
