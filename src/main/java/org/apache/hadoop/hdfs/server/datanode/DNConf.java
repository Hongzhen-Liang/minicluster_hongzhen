/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hdfs.server.datanode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.classification.InterfaceAudience;

import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_BLOCKREPORT_INITIAL_DELAY_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_BLOCKREPORT_INITIAL_DELAY_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_BLOCKREPORT_INTERVAL_MSEC_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_BLOCKREPORT_INTERVAL_MSEC_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_BLOCKREPORT_SPLIT_THRESHOLD_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_BLOCKREPORT_SPLIT_THRESHOLD_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_CACHEREPORT_INTERVAL_MSEC_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_CACHEREPORT_INTERVAL_MSEC_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_LIFELINE_INTERVAL_SECONDS_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_NON_LOCAL_LAZY_PERSIST;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_NON_LOCAL_LAZY_PERSIST_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_OUTLIERS_REPORT_INTERVAL_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_OUTLIERS_REPORT_INTERVAL_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_PMEM_CACHE_DIRS_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_PMEM_CACHE_RECOVERY_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_PMEM_CACHE_RECOVERY_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_PROCESS_COMMANDS_THRESHOLD_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_PROCESS_COMMANDS_THRESHOLD_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_ENCRYPT_DATA_OVERWRITE_DOWNSTREAM_DERIVED_QOP_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_ENCRYPT_DATA_OVERWRITE_DOWNSTREAM_DERIVED_QOP_KEY;
import static org.apache.hadoop.hdfs.client.HdfsClientConfigKeys.DFS_CLIENT_SOCKET_TIMEOUT_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_MAX_LOCKED_MEMORY_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_MAX_LOCKED_MEMORY_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_SOCKET_WRITE_TIMEOUT_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_SYNCONCLOSE_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_SYNCONCLOSE_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_TRANSFERTO_ALLOWED_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_TRANSFERTO_ALLOWED_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_XCEIVER_STOP_TIMEOUT_MILLIS_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_XCEIVER_STOP_TIMEOUT_MILLIS_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_MIN_SUPPORTED_NAMENODE_VERSION_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_MIN_SUPPORTED_NAMENODE_VERSION_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_ENCRYPT_DATA_TRANSFER_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_ENCRYPT_DATA_TRANSFER_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATA_ENCRYPTION_ALGORITHM_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_RESTART_REPLICA_EXPIRY_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_RESTART_REPLICA_EXPIRY_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.IGNORE_SECURE_PORTS_FOR_TESTING_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.IGNORE_SECURE_PORTS_FOR_TESTING_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_BP_READY_TIMEOUT_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_BP_READY_TIMEOUT_DEFAULT;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.client.HdfsClientConfigKeys;
import org.apache.hadoop.hdfs.protocol.HdfsConstants;
import org.apache.hadoop.hdfs.protocol.datatransfer.TrustedChannelResolver;
import org.apache.hadoop.hdfs.protocol.datatransfer.sasl.DataTransferSaslUtil;
import org.apache.hadoop.hdfs.server.common.Util;
import org.apache.hadoop.security.SaslPropertiesResolver;

import java.util.concurrent.TimeUnit;

/**
 * Simple class encapsulating all of the configuration that the DataNode
 * loads at startup time.
 */
@InterfaceAudience.Private
public class DNConf {
  final int socketTimeout;
  final int socketWriteTimeout;
  final int socketKeepaliveTimeout;
  public int transferSocketSendBufferSize;
  public int transferSocketRecvBufferSize;
  public boolean tcpNoDelay;

  public boolean transferToAllowed;
  boolean dropCacheBehindWrites;
  boolean syncBehindWrites;
  boolean syncBehindWritesInBackground;
  boolean dropCacheBehindReads;
  final boolean syncOnClose;
  public boolean encryptDataTransfer;
  boolean connectToDnViaHostname;
  final boolean overwriteDownstreamDerivedQOP;
  public boolean pmemCacheRecoveryEnabled;

  final long readaheadLength;
  public long heartBeatInterval;
  private final long lifelineIntervalMs;
  final long blockReportInterval;
  final long blockReportSplitThreshold;
  public boolean peerStatsEnabled;
  final boolean diskStatsEnabled;
  final long outliersReportIntervalMs;
  final long ibrInterval;
  final long initialBlockReportDelayMs;
  final long cacheReportInterval;
   long datanodeSlowIoWarningThresholdMs;

  final String minimumNameNodeVersion;
  String encryptionAlgorithm;
  final SaslPropertiesResolver saslPropsResolver;
  final TrustedChannelResolver trustedChannelResolver;
  public boolean ignoreSecurePortsForTesting;
  
  final long xceiverStopTimeout;
  final long restartReplicaExpiry;

  public  long processCommandsThresholdMs;

  final long maxLockedMemory;
  private final String[] pmemDirs;

  public  long bpReadyTimeout;

  // Allow LAZY_PERSIST writes from non-local clients?
  private final boolean allowNonLocalLazyPersist;

  public  int volFailuresTolerated;
  private final int volsConfigured;
  private final int maxDataLength;
  private Configurable dn;

  public DNConf(final Configurable dn) {
    this.dn = dn;
    socketTimeout = getConf().getInt(DFS_CLIENT_SOCKET_TIMEOUT_KEY,
        HdfsConstants.READ_TIMEOUT);
    socketWriteTimeout = getConf().getInt(DFS_DATANODE_SOCKET_WRITE_TIMEOUT_KEY,
        HdfsConstants.WRITE_TIMEOUT);
    socketKeepaliveTimeout = getConf().getInt(
        DFSConfigKeys.DFS_DATANODE_SOCKET_REUSE_KEEPALIVE_KEY,
        DFSConfigKeys.DFS_DATANODE_SOCKET_REUSE_KEEPALIVE_DEFAULT);
    this.transferSocketSendBufferSize = getConf().getInt(
        DFSConfigKeys.DFS_DATANODE_TRANSFER_SOCKET_SEND_BUFFER_SIZE_KEY,
        DFSConfigKeys.DFS_DATANODE_TRANSFER_SOCKET_SEND_BUFFER_SIZE_DEFAULT);
    this.transferSocketRecvBufferSize = getConf().getInt(
        DFSConfigKeys.DFS_DATANODE_TRANSFER_SOCKET_RECV_BUFFER_SIZE_KEY,
        DFSConfigKeys.DFS_DATANODE_TRANSFER_SOCKET_RECV_BUFFER_SIZE_DEFAULT);
    this.tcpNoDelay = getConf().getBoolean(
        DFSConfigKeys.DFS_DATA_TRANSFER_SERVER_TCPNODELAY,
        DFSConfigKeys.DFS_DATA_TRANSFER_SERVER_TCPNODELAY_DEFAULT);

    /* Based on results on different platforms, we might need set the default
     * to false on some of them. */
    transferToAllowed = getConf().getBoolean(
        DFS_DATANODE_TRANSFERTO_ALLOWED_KEY,
        DFS_DATANODE_TRANSFERTO_ALLOWED_DEFAULT);

    readaheadLength = getConf().getLong(
        HdfsClientConfigKeys.DFS_DATANODE_READAHEAD_BYTES_KEY,
        HdfsClientConfigKeys.DFS_DATANODE_READAHEAD_BYTES_DEFAULT);
    maxDataLength = getConf().getInt(DFSConfigKeys.IPC_MAXIMUM_DATA_LENGTH,
        DFSConfigKeys.IPC_MAXIMUM_DATA_LENGTH_DEFAULT);
    dropCacheBehindWrites = getConf().getBoolean(
        DFSConfigKeys.DFS_DATANODE_DROP_CACHE_BEHIND_WRITES_KEY,
        DFSConfigKeys.DFS_DATANODE_DROP_CACHE_BEHIND_WRITES_DEFAULT);
    syncBehindWrites = getConf().getBoolean(
        DFSConfigKeys.DFS_DATANODE_SYNC_BEHIND_WRITES_KEY,
        DFSConfigKeys.DFS_DATANODE_SYNC_BEHIND_WRITES_DEFAULT);
    syncBehindWritesInBackground = getConf().getBoolean(
        DFSConfigKeys.DFS_DATANODE_SYNC_BEHIND_WRITES_IN_BACKGROUND_KEY,
        DFSConfigKeys.DFS_DATANODE_SYNC_BEHIND_WRITES_IN_BACKGROUND_DEFAULT);
    dropCacheBehindReads = getConf().getBoolean(
        DFSConfigKeys.DFS_DATANODE_DROP_CACHE_BEHIND_READS_KEY,
        DFSConfigKeys.DFS_DATANODE_DROP_CACHE_BEHIND_READS_DEFAULT);
    connectToDnViaHostname = getConf().getBoolean(
        DFSConfigKeys.DFS_DATANODE_USE_DN_HOSTNAME,
        DFSConfigKeys.DFS_DATANODE_USE_DN_HOSTNAME_DEFAULT);
    this.blockReportInterval = getConf().getLong(
        DFS_BLOCKREPORT_INTERVAL_MSEC_KEY,
        DFS_BLOCKREPORT_INTERVAL_MSEC_DEFAULT);
    this.peerStatsEnabled = getConf().getBoolean(
        DFSConfigKeys.DFS_DATANODE_PEER_STATS_ENABLED_KEY,
        DFSConfigKeys.DFS_DATANODE_PEER_STATS_ENABLED_DEFAULT);
    this.diskStatsEnabled = Util.isDiskStatsEnabled(getConf().getInt(
        DFSConfigKeys.DFS_DATANODE_FILEIO_PROFILING_SAMPLING_PERCENTAGE_KEY,
        DFSConfigKeys.
            DFS_DATANODE_FILEIO_PROFILING_SAMPLING_PERCENTAGE_DEFAULT));
    this.outliersReportIntervalMs = getConf().getTimeDuration(
        DFS_DATANODE_OUTLIERS_REPORT_INTERVAL_KEY,
        DFS_DATANODE_OUTLIERS_REPORT_INTERVAL_DEFAULT,
        TimeUnit.MILLISECONDS);
    this.ibrInterval = getConf().getLong(
        DFSConfigKeys.DFS_BLOCKREPORT_INCREMENTAL_INTERVAL_MSEC_KEY,
        DFSConfigKeys.DFS_BLOCKREPORT_INCREMENTAL_INTERVAL_MSEC_DEFAULT);
    this.blockReportSplitThreshold = getConf().getLong(
        DFS_BLOCKREPORT_SPLIT_THRESHOLD_KEY,
        DFS_BLOCKREPORT_SPLIT_THRESHOLD_DEFAULT);
    this.cacheReportInterval = getConf().getLong(
        DFS_CACHEREPORT_INTERVAL_MSEC_KEY,
        DFS_CACHEREPORT_INTERVAL_MSEC_DEFAULT);

    this.datanodeSlowIoWarningThresholdMs = getConf().getLong(
        DFSConfigKeys.DFS_DATANODE_SLOW_IO_WARNING_THRESHOLD_KEY,
        DFSConfigKeys.DFS_DATANODE_SLOW_IO_WARNING_THRESHOLD_DEFAULT);

    long initBRDelay = getConf().getTimeDuration(
        DFS_BLOCKREPORT_INITIAL_DELAY_KEY,
        DFS_BLOCKREPORT_INITIAL_DELAY_DEFAULT,
        TimeUnit.SECONDS, TimeUnit.MILLISECONDS);
    if (initBRDelay >= blockReportInterval) {
      initBRDelay = 0;
      DataNode.LOG.info(DFS_BLOCKREPORT_INITIAL_DELAY_KEY + " is "
          + "greater than or equal to" + DFS_BLOCKREPORT_INTERVAL_MSEC_KEY
          + ".  Setting initial delay to 0 msec:");
    }
    initialBlockReportDelayMs = initBRDelay;
    
    heartBeatInterval = getConf().getTimeDuration(DFS_HEARTBEAT_INTERVAL_KEY,
        DFS_HEARTBEAT_INTERVAL_DEFAULT, TimeUnit.SECONDS,
        TimeUnit.MILLISECONDS);
    long confLifelineIntervalMs =
        getConf().getLong(DFS_DATANODE_LIFELINE_INTERVAL_SECONDS_KEY,
        3 * getConf().getTimeDuration(DFS_HEARTBEAT_INTERVAL_KEY,
        DFS_HEARTBEAT_INTERVAL_DEFAULT, TimeUnit.SECONDS,
            TimeUnit.MILLISECONDS));
    if (confLifelineIntervalMs <= heartBeatInterval) {
      confLifelineIntervalMs = 3 * heartBeatInterval;
      DataNode.LOG.warn(
          String.format("%s must be set to a value greater than %s.  " +
              "Resetting value to 3 * %s, which is %d milliseconds.",
              DFS_DATANODE_LIFELINE_INTERVAL_SECONDS_KEY,
              DFS_HEARTBEAT_INTERVAL_KEY, DFS_HEARTBEAT_INTERVAL_KEY,
              confLifelineIntervalMs));
    }
    lifelineIntervalMs = confLifelineIntervalMs;
    
    // do we need to sync block file contents to disk when blockfile is closed?
    this.syncOnClose = getConf().getBoolean(DFS_DATANODE_SYNCONCLOSE_KEY,
        DFS_DATANODE_SYNCONCLOSE_DEFAULT);

    this.minimumNameNodeVersion = getConf().get(
        DFS_DATANODE_MIN_SUPPORTED_NAMENODE_VERSION_KEY,
        DFS_DATANODE_MIN_SUPPORTED_NAMENODE_VERSION_DEFAULT);
    
    this.encryptDataTransfer = getConf().getBoolean(
        DFS_ENCRYPT_DATA_TRANSFER_KEY,
        DFS_ENCRYPT_DATA_TRANSFER_DEFAULT);
    this.overwriteDownstreamDerivedQOP = getConf().getBoolean(
        DFS_ENCRYPT_DATA_OVERWRITE_DOWNSTREAM_DERIVED_QOP_KEY,
        DFS_ENCRYPT_DATA_OVERWRITE_DOWNSTREAM_DERIVED_QOP_DEFAULT);
    this.encryptionAlgorithm = getConf().get(DFS_DATA_ENCRYPTION_ALGORITHM_KEY);
    this.trustedChannelResolver = TrustedChannelResolver.getInstance(getConf());
    this.saslPropsResolver = DataTransferSaslUtil.getSaslPropertiesResolver(
      getConf());
    this.ignoreSecurePortsForTesting = getConf().getBoolean(
        IGNORE_SECURE_PORTS_FOR_TESTING_KEY,
        IGNORE_SECURE_PORTS_FOR_TESTING_DEFAULT);
    
    this.xceiverStopTimeout = getConf().getLong(
        DFS_DATANODE_XCEIVER_STOP_TIMEOUT_MILLIS_KEY,
        DFS_DATANODE_XCEIVER_STOP_TIMEOUT_MILLIS_DEFAULT);

    this.maxLockedMemory = getConf().getLongBytes(
        DFS_DATANODE_MAX_LOCKED_MEMORY_KEY,
        DFS_DATANODE_MAX_LOCKED_MEMORY_DEFAULT);

    this.pmemDirs = getConf().getTrimmedStrings(
        DFS_DATANODE_PMEM_CACHE_DIRS_KEY);

    this.restartReplicaExpiry = getConf().getLong(
        DFS_DATANODE_RESTART_REPLICA_EXPIRY_KEY,
        DFS_DATANODE_RESTART_REPLICA_EXPIRY_DEFAULT) * 1000L;

    this.allowNonLocalLazyPersist = getConf().getBoolean(
        DFS_DATANODE_NON_LOCAL_LAZY_PERSIST,
        DFS_DATANODE_NON_LOCAL_LAZY_PERSIST_DEFAULT);

    this.bpReadyTimeout = getConf().getTimeDuration(
        DFS_DATANODE_BP_READY_TIMEOUT_KEY,
        DFS_DATANODE_BP_READY_TIMEOUT_DEFAULT, TimeUnit.SECONDS);

    this.volFailuresTolerated =
        getConf().getInt(
            DFSConfigKeys.DFS_DATANODE_FAILED_VOLUMES_TOLERATED_KEY,
            DFSConfigKeys.DFS_DATANODE_FAILED_VOLUMES_TOLERATED_DEFAULT);
    String[] dataDirs =
        getConf().getTrimmedStrings(DFSConfigKeys.DFS_DATANODE_DATA_DIR_KEY);
    this.volsConfigured = (dataDirs == null) ? 0 : dataDirs.length;

    this.pmemCacheRecoveryEnabled = getConf().getBoolean(
        DFS_DATANODE_PMEM_CACHE_RECOVERY_KEY,
        DFS_DATANODE_PMEM_CACHE_RECOVERY_DEFAULT);

    this.processCommandsThresholdMs = getConf().getTimeDuration(
        DFS_DATANODE_PROCESS_COMMANDS_THRESHOLD_KEY,
        DFS_DATANODE_PROCESS_COMMANDS_THRESHOLD_DEFAULT,
        TimeUnit.MILLISECONDS
    );
  }

  // We get minimumNameNodeVersion via a method so it can be mocked out in tests.
  String getMinimumNameNodeVersion() {
    return this.minimumNameNodeVersion;
  }
  
  /**
   * Returns the configuration.
   *
   * @return Configuration the configuration
   */
  public Configuration getConf() {
    return this.dn.getConf();
  }

  /**
   * Returns true if encryption enabled for DataTransferProtocol.
   *
   * @return boolean true if encryption enabled for DataTransferProtocol
   */
  public boolean getEncryptDataTransfer() {
    return encryptDataTransfer;
  }

  /**
   * Returns encryption algorithm configured for DataTransferProtocol, or null
   * if not configured.
   *
   * @return encryption algorithm configured for DataTransferProtocol
   */
  public String getEncryptionAlgorithm() {
    return encryptionAlgorithm;
  }

  public long getXceiverStopTimeout() {
    return xceiverStopTimeout;
  }

  public long getMaxLockedMemory() {
    return maxLockedMemory;
  }

  /**
   * Returns true if connect to datanode via hostname
   * 
   * @return boolean true if connect to datanode via hostname
   */
  boolean reverseBoolean(boolean variable,int num){
    if(num%2==1){
      return !variable;
    }else{
      return variable;
    }
  }

  int getConnectToDnViaHostnameTimes=0;
  public boolean getConnectToDnViaHostname() {
    connectToDnViaHostname=reverseBoolean(connectToDnViaHostname,getConnectToDnViaHostnameTimes);
    printMylog(getConnectToDnViaHostnameTimes++,"getConnectToDnViaHostname",connectToDnViaHostname+"");
    return connectToDnViaHostname;
  }

  /**
   * Returns socket timeout
   * 
   * @return int socket timeout
   */
  public int getSocketTimeout() {
    return socketTimeout;
  }

  /**
   * Returns socket write timeout
   * 
   * @return int socket write timeout
   */
  public int getSocketWriteTimeout() {
    return socketWriteTimeout;
  }

  /**
   * Returns the SaslPropertiesResolver configured for use with
   * DataTransferProtocol, or null if not configured.
   *
   * @return SaslPropertiesResolver configured for use with DataTransferProtocol
   */
  public SaslPropertiesResolver getSaslPropsResolver() {
    return saslPropsResolver;
  }

  /**
   * Returns the TrustedChannelResolver configured for use with
   * DataTransferProtocol, or null if not configured.
   *
   * @return TrustedChannelResolver configured for use with DataTransferProtocol
   */
  public TrustedChannelResolver getTrustedChannelResolver() {
    return trustedChannelResolver;
  }

  /**
   * Returns true if configuration is set to skip checking for proper
   * port configuration in a secured cluster.  This is only intended for use in
   * dev testing.
   *
   * @return true if configured to skip checking secured port configuration
   */
   //not in hdfs-default.xml
  public boolean getIgnoreSecurePortsForTesting() {
    return ignoreSecurePortsForTesting;
  }

  //not in hdfs-default.xml
  public boolean getAllowNonLocalLazyPersist() {
    return allowNonLocalLazyPersist;
  }

//  int getTransferSocketRecvBufferSizeTimes=0;
  public int getTransferSocketRecvBufferSize() {
//    getTransferSocketRecvBufferSizeTimes++;
//    printMylog(getTransferSocketRecvBufferSizeTimes,"getTransferSocketRecvBufferSize",transferSocketRecvBufferSize+"");
    return transferSocketRecvBufferSize;
  }

//  int getTransferSocketSendBufferSizeTimes=0;
  public int getTransferSocketSendBufferSize() {
//    getTransferSocketSendBufferSizeTimes++;
//    if(getTransferSocketSendBufferSizeTimes>1) {
//      mylog.error("getTransferSocketSendBufferSize "+ getTransferSocketSendBufferSizeTimes+" value: " + transferSocketSendBufferSize);
//    }else{
//      mylog.error("The first time calls  getTransferSocketSendBufferSize:"  + transferSocketSendBufferSize);
//    }
    return transferSocketSendBufferSize;
  }

  //Error
  //int getDataTransferServerTcpNoDelayTimes=0;
  public boolean getDataTransferServerTcpNoDelay(){
    //printMylog(getDataTransferServerTcpNoDelayTimes++,"getDataTransferServerTcpNoDelay",tcpNoDelay+"");
    return tcpNoDelay;
  }



//  int getBpReadyTimeoutTimes=0;
  public long getBpReadyTimeout() {
//    getBpReadyTimeoutTimes++;
//    if(getBpReadyTimeoutTimes>1) {
//      mylog.error("getBpReadyTimeout called again,times "+ getBpReadyTimeoutTimes+" value: " + bpReadyTimeout);
//    }else{
//      mylog.error("getBpReadyTimeout called:"  + bpReadyTimeout);
//    }
    return bpReadyTimeout;
  }

  /**
   * Returns the interval in milliseconds between sending lifeline messages.
   *
   * @return interval in milliseconds between sending lifeline messages
   */
  public long getLifelineIntervalMs() {
    return lifelineIntervalMs;
  }

  int getVolFailuresToleratedTimes=0;
  public int getVolFailuresTolerated() {
//    getVolFailuresToleratedTimes++;
    //printMylog(++getVolFailuresToleratedTimes,"getVolFailuresTolerated",volFailuresTolerated+"");
    return volFailuresTolerated;
  }

  public int getVolsConfigured() {
    return volsConfigured;
  }
//  int getSlowIoWarningThresholdMsTimes=0;
  public long getSlowIoWarningThresholdMs() {
//    printMylog(++getSlowIoWarningThresholdMsTimes,"getSlowIoWarningThresholdMs",datanodeSlowIoWarningThresholdMs+"");
    return datanodeSlowIoWarningThresholdMs;
  }

  int getMaxDataLength() {
    return maxDataLength;
  }

  public String[] getPmemVolumes() {
    return pmemDirs;
  }

  int getPmemCacheRecoveryEnabledTimes=0;
  public boolean getPmemCacheRecoveryEnabled() {
    pmemCacheRecoveryEnabled=reverseBoolean(pmemCacheRecoveryEnabled,getPmemCacheRecoveryEnabledTimes);
    printMylog(getPmemCacheRecoveryEnabledTimes++,"getPmemCacheRecoveryEnabled",pmemCacheRecoveryEnabled+"");
    return pmemCacheRecoveryEnabled;
  }

//  int getProcessCommandsThresholdMsTimes=0;
  public long getProcessCommandsThresholdMs() {
//    printMylog(++getProcessCommandsThresholdMsTimes,"getProcessCommandsThresholdMs",processCommandsThresholdMs+"");
    return processCommandsThresholdMs;
  }

  int getDropCacheBehindWritesTimes=0;
  public boolean getDropCacheBehindWrites(){
    dropCacheBehindWrites=reverseBoolean(dropCacheBehindWrites,getDropCacheBehindWritesTimes);
    printMylog(getDropCacheBehindWritesTimes++,"getDropCacheBehindWrites",dropCacheBehindWrites+"");
    return dropCacheBehindWrites;
  }

  //Error
  //int getSyncBehindWritesTimes=0;
  public boolean getSyncBehindWrites(){
    //printMylog(getSyncBehindWritesTimes++,"getSyncBehindWrites",syncBehindWrites+"");
    return syncBehindWrites;
  }

  int syncBehindWritesInBackgroundTimes=0;
  public boolean getSyncBehindWritesInBackground(){
    syncBehindWritesInBackground=reverseBoolean(syncBehindWritesInBackground,syncBehindWritesInBackgroundTimes);
    printMylog(syncBehindWritesInBackgroundTimes++,"getSyncBehindWritesInBackground",syncBehindWritesInBackground+"");
    return syncBehindWritesInBackground;
  }
  int dropCacheBehindReadsTimes=0;
  public boolean getDropCacheBehindReads(){
    dropCacheBehindReads=reverseBoolean(dropCacheBehindReads,dropCacheBehindReadsTimes);
    printMylog(dropCacheBehindReadsTimes++,"getDropCacheBehindReads",dropCacheBehindReads+"");
    return dropCacheBehindReads;
  }
  int peerStatsEnabledTime=0;
  public boolean getPeerStatsEnabled(){
    peerStatsEnabled=reverseBoolean(peerStatsEnabled,peerStatsEnabledTime);
    printMylog(peerStatsEnabledTime++,"getPeerStatsEnabled",peerStatsEnabled+"");
    return peerStatsEnabled;
  }

  //Error TestConnCache
  int getTransferToAllowedTimes=0;
  public boolean getTransferToAllowed(){
//    transferToAllowed=reverseBoolean(transferToAllowed,getTransferToAllowedTimes);
//    printMylog(getTransferToAllowedTimes++,"getTransferToAllowed",transferToAllowed+"");
    return transferToAllowed;
  }

  public void printMylog(int counter,String functionName, String value){
    Log mylog = LogFactory.getLog("MyLog");
    if(counter>0) {
      mylog.error(functionName+" called again,times "+ counter+" value: " + value);
    }else{
      mylog.error(functionName+" called:"  + value);
    }
  }
}
