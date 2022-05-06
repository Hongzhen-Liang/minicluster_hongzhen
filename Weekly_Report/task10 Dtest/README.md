1. mvn -Dtest=CLASS_NAME#FUNCTION_NAME test
2. https://github.com/apache/hadoop/blob/trunk/BUILDING.txt
3. https://maven.apache.org/surefire/maven-surefire-plugin/examples/single-test.html

* If you want to test all unit test, you can run ‘mvn test’ in the root directory of the project.

# About Log
1. setting:
	```
	log4j.appender.E = org.apache.log4j.FileAppender
	log4j.appender.E.File =${hadoop.log.dir}/HongzhenDebug.log
	log4j.appender.E.Append = false
	log4j.appender.E.Threshold = ERROR
	log4j.appender.E.layout = org.apache.log4j.PatternLayout
	log4j.appender.E.layout.ConversionPattern = %-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
	```
2. Target:
	* target/log/HongzhenDebug.log

# seems to be used for wait
	```
	    GenericTestUtils.waitFor(new Supplier<Boolean>() {
      @Override
      public Boolean get() {
        return (long) datanodes.get(0).getBalancerBandwidth() == newBandwidth
            && (long) datanodes.get(1).getBalancerBandwidth() == newBandwidth;
      }
    }, 100, 60 * 1000);
	```


# Test
	1. Unsafe:
		1. Encrypt:
			* in order to activate encrypted: dfs.encrypt.data.transfer=true && dfs.block.access.token.enable=true
			1. dfs.encrypt.data.transfer
			2. dfs.block.access.token.enable
			2. dfs.encrypt.data.transfer.algorithm
		2. HTTPS:
			1. dfs.http.policy and dfs.https.server.keystore.resource
			* It was assigned in DFSUtil.java. For NameNode it was used only once in NameNodeHttpServer.
			* namenode <-> datanode=rpc, namenode <-> client=rpc, client <-> datanode=socket
			* If it sets either of them to HTTPS ONLY, the whole communication would be stucked
		3. balancer
			1. dfs.datanode.balance.max.concurrent.moves
			* TestDataNodeReconfiguration.java -> testFailedDecreaseConcurrentMovers, a scenario where the DataNode has been reconfigured with fewer mover threads, but all of the current treads are busy.
		4. dfs.datanode.failed.volumes.tolerated: 
			1. why it unsafe: If its failed volumes tolerated diminishing, it would probably dead while processing data.
			2. reference: https://community.pivotal.io/s/article/datanode-is-shutting-down-reported-due-to-datanode-failed-volumes?language=en_US
	
	2. Safe:
		1. Only affect multi-datanode:
			1. Used:
				1. Buffer
					1. dfs.datanode.transfer.socket.send.buffer.size
					2. dfs.datanode.transfer.socket.recv.buffer.size
					* reason: By Changing the DNConf we can alter the data. The new value would only affect the next time connection(Use for transfering data) between datanode.
				2. dfs.datanode.use.datanode.hostname:
					* Map: DataXceiver.java -> this.connectToDnViaHostname
					* reason: the value was called only in the setup state of every DataXceiver. Therefore, change this value would only affect the next time connection.
					* Fails: TestHdfsNativeCodeLoader,TestHDFSCLI
			
	3. InProgressing:
		2. dfs.datanode.bp-ready.timeout
		3. log:
			* reason: it only affects when to print a log, which is harmless to the whole program.
			* reference: by looking the source code.
			1. dfs.datanode.processcommands.threshold
			2. dfs.datanode.slow.io.warning.threshold.ms
	4. MVN test problem:
		0. run for almost 5 hours
		1. TestHdfsNativeCodeLoader, TestFileCreation
		2. TestDFSUtil.testGetHaNnHttpAddresses
		3. TestDFSUtil.testHANameNodesWithFederation
		4. TestDirectoryScanner.testThrottling
		5. TestFsVolumeList.testAddRplicaProcessorForAddingReplicaInMap
		6. TestWebHdfsTimeouts.testAuthUrlConnectTimeout
		7. TestWebHdfsTimeouts.testConnectTimeout
		8. TestWebHdfsTimeouts.testRedirectConnectTimeout
		9. TestWebHdfsTimeouts.testTwoStepWriteConnectTimeout
		10. TestFileCreation.testFileCreationNamenodeRestart
		11. TestStoragePolicyPermissionSettings.testStoragePolicyPermissionAdmins
		12. TestStoragePolicyPermissionSettings.testStoragePolicyPermissionDefault
		13. TestStoragePolicyPermissionSettings.testStoragePolicyPermissionDisabled
		14. TestDataNodeRollingUpgrade.testWithLayoutChangeAndFinalize
		15. TestDataNodeRollingUpgrade.testWithLayoutChangeAndRollback
		16. TestObserverReadProxyProvider.testObserverRetriableException
		17. TestObserverReadProxyProvider.testObserverToActive:224->doWrite:332->doWrite:341 » NullPointer
		18.	TestObserverReadProxyProvider.testObserverToStandby:270->doRead:328->doRead:345 » NullPointer
		19. TestObserverReadProxyProvider.testReadOperationOnObserver:159->doRead:328->doRead:345 » NullPointer
		20. TestObserverReadProxyProvider.testSingleObserverToStandby:292->doRead:328->doRead:345 » NullPointer
		21. TestObserverReadProxyProvider.testUnreachableObserverWithMultiple:192->doRead:328->doRead:345 » NullPointer
		22. TestObserverReadProxyProvider.testUnreachableObserverWithNoBackup:181->doRead:328->doRead:345 » NullPointer
		23. TestObserverReadProxyProvider.testWriteOperationOnActive:169->doWrite:332->doWrite:341 » NullPointer