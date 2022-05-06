* Map: NameNode -> FSNamesystem->BlockManager
				 ->DataNodeManager 
				 ->FSImageFormatProtobuf
1. NameNode reconfiguration
	1. hadoop.user.group.metrics.percentiles.intervals
	2. dfs.metrics.percentiles.intervals
	3. dfs.namenode.gc.time.monitor.enable
	4. dfs.namenode.gc.time.monitor.observation.window.ms
	5. dfs.namenode.gc.time.monitor.sleep.interval.ms
	6. dfs.namenode.provided.enabled
	7. dfs.provided.aliasmap.inmemory.enabled
	8. dfs.namenode.metrics.logger.period.seconds
	9. dfs.namenode.plugins
	10. dfs.namenode.plugins
	11. fs.trash.interval
	12. dfs.ha.nn.not-become-active-in-safemode
	13. dfs.reformat.disabled
	14. dfs.namenode.support.allow.format
	15. dfs.namenode.shared.edits.dir
	16. dfs.namenode.startup

3. DatanodeManager datanodemanager:
	* Once it created most of the parameter should be in memory and not read from conf agian
	1. dfs.use.dfs.network.topology
	2. dfs.datanode.peer.stats.enabled
	3. dfs.datanode.fileio.profiling.sampling.percentage 
		```
		this.dataNodeDiskStatsEnabled = Util.isDiskStatsEnabled(conf.getInt(
        DFSConfigKeys.DFS_DATANODE_FILEIO_PROFILING_SAMPLING_PERCENTAGE_KEY,
        DFSConfigKeys.
            DFS_DATANODE_FILEIO_PROFILING_SAMPLING_PERCENTAGE_DEFAULT));
		```
	4. dfs.namenode.block-placement-policy.exclude-slow-nodes.enabled
	5. dfs.namenode.max.slowpeer.collect.nodes
	6. dfs.namenode.slowpeer.collect.interval
	7. dfs.namenode.reject-unresolved-dn-topology-mapping
	8. dfs.heartbeat.interval
	9. dfs.namenode.heartbeat.recheck-interval
	10. dfs.block.invalidate.limit
	11. dfs.namenode.avoid.read.stale.datanode
	12. dfs.namenode.read.considerLoad
	13. dfs.namenode.read.considerStorageType
	14. dfs.namenode.avoid.write.stale.datanode
	15. dfs.namenode.write.stale.datanode.ratio
	16. dfs.namenode.path.based.cache.retry.interval.ms
	17. dfs.namenode.blocks.per.postponedblocks.rescan
	18. dfs.namenode.stale.datanode.interval
	19. dfs.heartbeat.interval
	20. dfs.namenode.stale.datanode.minimum.interval

4. FSNamesystem namesystem:
	1. dfs.namenode.audit.log.async
	2. dfs.namenode.resource.check.interval
	3. dfs.permissions.superusergroup
	4. dfs.storage.policy.enabled
	5. dfs.storage.policy.permissions.superuser-only
	6. dfs.namenode.snapshotdiff.listing.limit
	7. dfs.checksum.type
	8. this.serverDefaults 
		1. dfs.blocksize
		2. dfs.bytes-per-checksum
		3. dfs.client-write-packet-size
		4. dfs.replication
		5. io.file.buffer.size
		6. dfs.encrypt.data.transfer
		7. fs.trash.interval
		8. hadoop.security.key.provider.path
	9. dfs.namenode.max.objects
	10. dfs.namenode.fs-limits.min-block-size
	11. dfs.namenode.fs-limits.max-blocks-per-file
	12. dfs.batched.ls.limit
	13. dfs.namenode.file.close.num-committed-allowed
	14. dfs.namenode.max-corrupt-file-blocks-returned
	15. dfs.ha.standby.checkpoints
	16. dfs.namenode.edit.log.autoroll.multiplier.threshold
	17. dfs.namenode.checkpoint.txns
	18. dfs.namenode.edit.log.autoroll.check.interval.ms
	19. dfs.namenode.lazypersist.file.scrub.interval.sec
	20. dfs.namenode.edekcacheloader.initial.delay.ms
	21. dfs.namenode.edekcacheloader.interval.ms
	22. dfs.namenode.lease-recheck-interval-ms
	23. dfs.namenode.max-lock-hold-to-release-lease-ms
	24. dfs.namenode.delegation.token.always-use
	25. dfs.namenode.inode.attributes.provider.class
	26. dfs.namenode.list.openfiles.num.responses
	27. dfs.permissions.allow.owner.set.quota
	28. dfs.namenode.block.deletion.increment
	29. dfs.namenode.enable.retrycache
	30. dfs.namenode.retrycache.heap.percent
	31. dfs.namenode.retrycache.expirytime.millis
	32. dfs.namenode.audit.loggers
	33. dfs.namenode.shared.edits.dir
	34. DelegationTokenSecretManager
		1. dfs.namenode.delegation.key.update-interval
		2. dfs.namenode.delegation.token.max-lifetime
		3. dfs.namenode.delegation.token.renew-interval
		4. dfs.namenode.audit.log.token.tracking.id
	35. hadoop.caller.context.enabled
	36. hadoop.caller.context.max.size
	37. hadoop.caller.context.signature.max.size
	38. dfs.namenode.audit.log.token.tracking.id
	39. dfs.namenode.audit.log.debug.cmdlist
	40. dfs.namenode.audit.log.async.blocking
	41. dfs.namenode.audit.log.async.buffer.size
5. NameNodeRpcServer rpcServer
	1. dfs.namenode.handler.count
	2. ipc.maximum.data.length
	3. dfs.namenode.service.handler.count
	4. dfs.namenode.lifeline.handler.count
	5. dfs.namenode.lifeline.handler.ratio
	6. dfs.namenode.state.context.enabled
	7. hadoop.security.authorization
	8. dfs.namenode.min.supported.datanode.version
	9. dfs.namenode.ec.system.default.policy
	10. dfs.namenode.rpc-address.auxiliary-ports
6. BlockManager bm:
	1. dfs.namenode.startup.delay.block.deletion.sec
	2. dfs.namenode.reconstruction.pending.timeout-sec
	3. dfs.corruptfilesreturned.max
	4. dfs.replication.max
	5. dfs.replication.max
	6. dfs.namenode.replication.min
	7. dfs.namenode.replication.max-streams
	8. dfs.namenode.replication.max-streams-hard-limit
	9. dfs.namenode.redundancy.interval.seconds
	10. dfs.namenode.storageinfo.defragment.interval.ms
	11. dfs.namenode.storageinfo.defragment.timeout.ms
	12. dfs.namenode.storageinfo.defragment.ratio
	13. dfs.encrypt.data.transfer
	14. dfs.namenode.max-num-blocks-to-log
	15. dfs.block.misreplication.processing.limit
	16. dfs.namenode.maintenance.replication.min
	17. dfs.namenode.redundancy.queue.restart.iterations
	18. dfs.heartbeat.interval
	19. dfs.namenode.blockreport.queue.size
	20. dfs.namenode.corrupt.block.delete.immediately.enabled
	21. dfs.block.access.token.enable
	22. dfs.block.access.key.update.interval
	23. dfs.block.access.token.lifetime
	24. dfs.encrypt.data.transfer.algorithm
	25. dfs.block.access.token.protobuf.enable
	26. dfs.namenode.send.qop.enabled
	27. dfs.storage.policy.enabled  * 
	28. dfs.storage.policy.satisfier.mode
7. FSImageFormatProtobuf
	1. dfs.image.parallel.threads
	2. dfs.image.compress
	3. dfs.image.parallel.load
	4. dfs.image.parallel.inode.threshold
	5. dfs.image.parallel.target.sections
	