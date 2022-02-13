#!/bin/bash

JAVA_HOME="/opt/module/jdk-15.0.2/"
MAVEN_HOME="/opt/module/apache-maven-3.8.2"
export PATH="$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH"


if [[ ! -d "$target/surefire-reports" ]]; then
 echo "File not exists"
else
 rm -r target/surefire-reports
fi

# "./combination.sh dfs.block.access.token.enable,true:dfs.encrypt.data.transfer,true ALL"
property_newValue=$1
DTest=$2

javac BroadCastClient.java
nohup java BroadCastClient $property_newValue &

if [ "$DTest" == "ALL" ]||[ -z "$DTest" ];then
	mvn test
else
	mvn -Dtest=$DTest test
fi

pid=$(jps | grep BroadCastClient | awk '{print $1}')
kill -9 $pid

#python3 analyze/Error_Analyze.py $property_newValue $DTest
