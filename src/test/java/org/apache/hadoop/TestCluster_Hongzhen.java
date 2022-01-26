package org.apache.hadoop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.server.datanode.DataNode;
import org.junit.Test;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.fs.FileSystem;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class TestCluster_Hongzhen{
    //@Test
    public void testCluster() throws Exception {
        Log mylog = LogFactory.getLog("MyLog");
        Configuration conf = new HdfsConfiguration();
        MiniDFSCluster cluster = new MiniDFSCluster.Builder(conf).numDataNodes(1).build();
        // add a second datanode to the cluster
        cluster.startDataNodes(conf, 1, true, null, null, null, null);
        DataNode dn = cluster.getDataNodes().get(0);
        FileSystem fs = cluster.getFileSystem();
        mylog.error("start sleep");
//        while(true){
//            //mylog.error("EncryptDataTransfer="+dn.getDnConf().getEncryptDataTransfer());
//            TimeUnit.SECONDS.sleep(10);
//        }

//        fs.close();
//        cluster.shutdown();
    }
}