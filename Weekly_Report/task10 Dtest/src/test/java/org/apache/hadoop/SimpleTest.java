package org.apache.hadoop;

import org.apache.log4j.Logger;
import org.junit.Test;

public class SimpleTest {
    @Test
    public void testPrint(){
        Logger logger = Logger.getLogger(SimpleTest.class);
        logger.info("SimpleTest");
        System.out.println("test");
    }
}
