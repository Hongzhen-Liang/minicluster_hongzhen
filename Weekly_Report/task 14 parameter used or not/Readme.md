
-1. Machine:
	0. LHZ@c220g1-030611.wisc.cloudlab.us
	1. LHZ@c220g1-030601.wisc.cloudlab.us
	2. LHZ@c220g1-030608.wisc.cloudlab.us
0. progressing:
	1. possibly unsafe:
		1. dfs.http.policy,HTTPS_ONLY
		2. dfs.client.https.need-auth,true
		3. dfs.namenode.fs-limits.max-component-length,20
		4. dfs.namenode.fs-limits.max-directory-items,500 (failed should be expected
		5. dfs.namenode.fs-limits.min-block-size,1548576
		6. dfs.namenode.fs-limits.max-blocks-per-file,500
	2. possibly safe
		1. dfs.datanode.balance.max.concurrent.moves(w
		2. dfs.datanode.slow.io.warning.threshold.ms(r-node1
	3. batch test:
		1. Boolean
			1. NameNode:
				1. dfs.namenode.redundancy.considerLoad,false
				2. dfs.namenode.redundancy.considerLoadByStorageType,true = DFS_NAMENODE_REDUNDANCY_CONSIDERLOAD_KEY
				3. dfs.namenode.read.considerStorageType,true
				4. dfs.namenode.name.dir.restore,true
				5. dfs.permissions.enabled,false
				6. dfs.permissions.ContentSummary.subAccess,true
				7. dfs.namenode.acls.enabled,false
				8. dfs.namenode.posix.acl.inheritance.enabled,false
				9. dfs.block.access.token.enable,true
				10. dfs.block.access.token.protobuf.enable,true(Last
				
			2. DataNode:
				0. not in dnconf:
					1. dfs.datanode.fixed.volume.size,true(Not in datanode)
					2. dfs.datanode.lock.read.write.enabled,false(Not in datanode
					3. dfs.datanode.lock.fair,false(Not in datanoe
					4. dfs.datanode.peer.metrics.min.outlier.detection.samples
					5. dfs.datanode.metrics.logger.period.seconds
					6. dfs.datanode.block-pinning.enabled
				
		2. Numerated
			1. NameNode:
				1. dfs.datanode.handler.count,1
				2. dfs.namenode.heartbeat.recheck-interval,300
				3. dfs.client.cached.conn.retry,1
				4. dfs.namenode.read.considerLoad,true <-> dfs.namenode.redundancy.considerLoad.factor,1.0
				5. dfs.default.chunk.view.size,1000
				6. dfs.datanode.du.reserved,10
				7. dfs.datanode.du.reserved.pct,10
				8. dfs.namenode.lazypersist.file.scrub.interval.sec,0
				9. dfs.block.access.key.update.interval,100
				10. dfs.block.access.token.lifetime,100
			2. DataNode:
				1. INT:
					1. dfs.client.socket-timeout
					2. dfs.datanode.socket.write.timeout
					3. dfs.datanode.socket.reuse.keepalive
					4. dfs.datanode.transfer.socket.send.buffer.size
					5. dfs.datanode.transfer.socket.recv.buffer.size
					6. ipc.maximum.data.length
					7. dfs.datanode.fileio.profiling.sampling.percentage
					8. dfs.datanode.failed.volumes.tolerated
				2. Long:
					1. dfs.datanode.readahead.bytes
					2. dfs.blockreport.intervalMsec
					3. dfs.blockreport.split.threshold
					4. dfs.cachereport.intervalMsec
					5. dfs.datanode.slow.io.warning.threshold.ms
					6. dfs.datanode.xceiver.stop.timeout.millis
					7. dfs.datanode.restart.replica.expiration
				3. TimeDuration:
					1. dfs.datanode.outliers.report.interval
					2. dfs.blockreport.incremental.intervalMsec
					3. dfs.blockreport.initialDelay
					4. dfs.heartbeat.interval
					5. dfs.datanode.bp-ready.timeout
					6. dfs.datanode.processcommands.threshold
				4. LongByte:
					1. dfs.datanode.max.locked.memory
				1. dfs.datanode.restart.replica.expiration,5..20..1..100
				2. dfs.datanode.data.transfer.bandwidthPerSec
				3. dfs.datanode.data.write.bandwidthPerSec
				4. dfs.datanode.max.locked.memory
				5. dfs.datanode.fsdatasetcache.max.threads.per.volume,2
				6. dfs.datanode.lazywriter.interval.sec,10
				7. dfs.datanode.network.counts.cache.max.size,147483647
				8. dfs.datanode.replica.cache.expiry.time,1m
				9. dfs.datanode.cached-dfsused.check.interval.ms,600
				10. dfs.namenode.safemode.min.datanodes,1
				11. dfs.datanode.volumes.replica-add.threadpool.size,1
				12. dfs.datanode.socket.reuse.keepalive,100
				13. dfs.datanode.oob.timeout-ms
				14. dfs.datanode.cache.revocation.timeout.ms,1000
				15. dfs.datanode.cache.revocation.polling.ms,100
				16. dfs.datanode.processcommands.threshold,1s
				17. dfs.datanode.lock-reporting-threshold-ms,100
				18. dfs.datanode.fileio.profiling.sampling.percentage，10
				19. dfs.datanode.outliers.report.interval
				20. dfs.blockreport.incremental.intervalMsec
				21. dfs.blockreport.split.threshold
				22. dfs.cachereport.intervalMsec
				23. dfs.datanode.slow.io.warning.threshold.ms
				24. dfs.blockreport.initialDelay
				25. dfs.heartbeat.interval
	4. Don't know the reconfigure value
		1. hadoop.hdfs.configuration.version
		2. dfs.namenode.rpc-address
		3. dfs.namenode.rpc-bind-host
		4. dfs.namenode.servicerpc-address
		5. dfs.namenode.servicerpc-bind-host
		6. dfs.namenode.lifeline.rpc-address
		7. dfs.namenode.lifeline.rpc-bind-host
		8. dfs.namenode.secondary.http-address
		9. dfs.namenode.secondary.https-address
		10. dfs.datanode.address
		11. dfs.datanode.http.address
		12. dfs.datanode.ipc.address
		13. dfs.datanode.http.internal-proxy.port
		14. dfs.namenode.http-address
		15. dfs.namenode.http-bind-host
		16. dfs.https.server.keystore.resource
		17. dfs.client.https.keystore.resource
		18. dfs.datanode.https.address
		19. dfs.namenode.https-address
		20. dfs.namenode.https-bind-host
		21. dfs.datanode.dns.interface
		22. dfs.datanode.dns.nameserver
		23. dfs.namenode.backup.address
		24. dfs.namenode.backup.http-address
		25. dfs.datanode.httpserver.filter.handlers
		26. dfs.datanode.du.reserved.calculator
		27. dfs.namenode.name.dir
		28. dfs.namenode.edits.dir
		29. dfs.namenode.edits.dir.required
		30. dfs.namenode.shared.edits.dir
		31. dfs.namenode.edits.journal-plugin.qjournal
		32. dfs.permissions.superusergroup
		33. dfs.cluster.administrators
		34. dfs.datanode.ram.disk.replica.tracker
		35. dfs.datanode.replica.cache.root.dir
		36. dfs.datanode.pmem.cache.dirs
	5. Not Found in hdfs-default.xml
		1. dfs.datanode.non.local.lazy.persist
		2. dfs.datanode.duplicate.replica.deletion
		3. dfs.datanode.synconclose
1. Error:
	0. Wrongly use:
		1. dfs.datanode.sync.behind.writes,true : TestDataNodeVolumeMetrics
		2. dfs.datanode.transferTo.allowed,false(F)TestConnCache
		3. dfs.data.transfer.server.tcpnodelay,false(F)TestDataNodeTcpNoDelay
	1. Multiplt times:
		1. dfs.encrypt.data.transfer,true
	2. Not read again:
		1. dfs.datanode.transfer.socket.recv.buffer.size,1
2. Not Error:
	1. Multiplt times:
		1. dfs.block.access.token.enable,true:dfs.encrypt.data.transfer,true
		2. dfs.datanode.use.datanode.hostname
		3. dfs.datanode.transfer.socket.send.buffer.size
		4. dfs.datanode.failed.volumes.tolerated,-1(I think it should failed instead)
		5. dfs.datanode.processcommands.threshold,1
		6. dfs.datanode.drop.cache.behind.writes,true
		7. dfs.datanode.sync.behind.writes.in.background
		8. dfs.datanode.drop.cache.behind.reads,true
	2. Not read again:
		1. dfs.datanode.bp-ready.timeout,1
	3. Not even read:
		1. dfs.datanode.peer.stats.enabled,true
		2. dfs.datanode.pmem.cache.recovery,false