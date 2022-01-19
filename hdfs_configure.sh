#!/bin/bash

node_0="LHZ@c220g1-030824.wisc.cloudlab.us"
node_1="LHZ@c220g1-030823.wisc.cloudlab.us"
node_2="LHZ@c220g1-030826.wisc.cloudlab.us"


for node in $node_0 $node_1 $node_2
do
rsync target/hadoop-hdfs-3.3.1.jar target/hadoop-hdfs-3.3.1-sources.jar target/hadoop-hdfs-3.3.1-tests.jar target/hadoop-hdfs-3.3.1-test-sources.jar $node:~
ssh $node "rsync hadoop-hdfs-3.3.1.jar hadoop-hdfs-3.3.1-sources.jar hadoop-hdfs-3.3.1-tests.jar hadoop-hdfs-3.3.1-test-sources.jar /opt/module/hadoop-3.3.1/share/hadoop/hdfs"
ssh $node "rm hadoop-hdfs-3.3.1.jar hadoop-hdfs-3.3.1-sources.jar hadoop-hdfs-3.3.1-tests.jar hadoop-hdfs-3.3.1-test-sources.jar"
done


echo " =======restart hadoop======"
echo " =======stop hadoop======"
echo " -------close historyserver------"
ssh $node_0 "/opt/module/hadoop-3.3.1/bin/mapred --daemon stop historyserver"
echo " -------close yarn---------------"
ssh $node_1 "/opt/module/hadoop-3.3.1/sbin/stop-yarn.sh"
echo " -------close hdfs---------------"
ssh $node_2 "/opt/module/hadoop-3.3.1/sbin/stop-dfs.sh"
echo "------start hdfs-------"
ssh $node_0 "/opt/module/hadoop-3.3.1/sbin/start-dfs.sh"
echo "------start yarn-------"
ssh $node_1 "/opt/module/hadoop-3.3.1/sbin/start-yarn.sh"
echo "------start historyserver-----"
ssh $node_2 "/opt/module/hadoop-3.3.1/bin/mapred --daemon start historyserver"
