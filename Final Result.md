0. boolean:
	1. dfs.encrypt.data.transfer
	2. dfs.datanode.use.datanode.hostname
	3. ignore.secure.ports.for.testing(Not exits)
	4. dfs.datanode.non.local.lazy.persist(Not exits)
	5. dfs.data.transfer.server.tcpnodelay(Error)
	6. dfs.datanode.pmem.cache.recovery
	7. dfs.datanode.drop.cache.behind.writes
	8. dfs.datanode.sync.behind.writes(Error)
	9. dfs.datanode.sync.behind.writes.in.background
	10. dfs.datanode.drop.cache.behind.reads
	11. dfs.datanode.peer.stats.enable
	12. dfs.datanode.transferTo.allowed(Error)
	* TestPmemCacheRecovery,TestDFSAdminWithHA
1. int:
	1. dfs.client.socket-timeout socketTimeout
	2. dfs.datanode.socket.write.timeout socketWriteTimeout
	3. dfs.datanode.transfer.socket.recv.buffer.size transferSocketRecvBufferSize
	4. dfs.datanode.transfer.socket.send.buffer.size transferSocketSendBufferSize
	5. dfs.datanode.failed.volumes.tolerated volFailuresTolerated
	6. dfs.datanode.data.dir(strange
	7. ipc.maximum.data.length  maxDataLength
2. long:
	1. dfs.datanode.max.locked.memory maxLockedMemory
	2. dfs.datanode.bp-ready.timeout bpReadyTimeout
	3. dfs.datanode.lifeline.interval.seconds lifelineIntervalMs
	4. dfs.datanode.slow.io.warning.threshold.ms   datanodeSlowIoWarningThresholdMs
	5. dfs.datanode.processcommands.threshold   processCommandsThresholdMs
	
	org.apache.hadoop.hdfs.server.datanode.TestDataNodeVolumeFailureToleration & org.apache.hadoop.hdfs.server.datanode.TestDataNodeVolumeFailure & TestDataNodeTransferSocketSize

3. Directly used by Datanode:
	1. dfs.datanode.plugins
	2. dfs.datanode.directoryscan.interval
	3. dfs.domain.socket.path
	4. dfs.client.use.legacy.blockreader.local
	5. dfs.datanode.transfer.socket.recv.buffer.size
	6. dfs.block.access.token.enable
	7. dfs.datanode.data.dir
	8. dfs.datanode.startup
	








1. Error:
	0. Wrongly use:
		1. dfs.datanode.sync.behind.writes,true : TestDataNodeVolumeMetrics
		2. dfs.datanode.transferTo.allowed,false(F)TestConnCache
		3. dfs.data.transfer.server.tcpnodelay,false(F)TestDataNodeTcpNoDelay
	1. Multiplt times:
		1. dfs.encrypt.data.transfer,true
		2. dfs.datanode.failed.volumes.tolerated,0 :TestDataNodeVolumeFailureToleration,TestDataNodeVolumeFailure
	2. Not read again:
		1. dfs.datanode.transfer.socket.recv.buffer.size,1
	3. Not sure:
		1. dfs.datanode.transfer.socket.recv.buffer.size
		* [ERROR]   TestDataNodeTransferSocketSize.testSpecifiedDataSocketSize:43 Receive buffer size should be 4K expected:<4096> but was:<40970>
2. Not Error:
	1. Multiplt times:
		1. dfs.block.access.token.enable,true:dfs.encrypt.data.transfer,true
		2. dfs.datanode.use.datanode.hostname
		3. dfs.datanode.transfer.socket.send.buffer.size
		5. dfs.datanode.processcommands.threshold,1
		6. dfs.datanode.drop.cache.behind.writes,true
		7. dfs.datanode.sync.behind.writes.in.background
		8. dfs.datanode.drop.cache.behind.reads,true
		9. dfs.client.socket-timeout
		10. dfs.datanode.socket.write.timeout
		11. dfs.datanode.max.locked.memory
		12. dfs.datanode.lifeline.interval.seconds
		13. ipc.maximum.data.length
	2. Not read again:
		1. dfs.datanode.bp-ready.timeout,1
	3. Not even read:
		1. dfs.datanode.peer.stats.enabled,true
		2. dfs.datanode.pmem.cache.recovery,false
		3. dfs.datanode.slow.io.warning.threshold.ms

* Sixiang's:
	1. org.apache.hadoop.TestGenericRefresh
	2. org.apache.hadoop.TestRefreshCallQueue
	3. org.apache.hadoop.cli.TestAclCLI
	4. org.apache.hadoop.cli.TestAclCLIWithPosixAclInheritance
	5. org.apache.hadoop.cli.TestCacheAdminCLI
	6. org.apache.hadoop.cli.TestCryptoAdminCLI
	7. org.apache.hadoop.cli.TestDeleteCLI
	8. org.apache.hadoop.cli.TestErasureCodingCLI
	9. org.apache.hadoop.cli.TestHDFSCLI