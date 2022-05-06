package space.sinscry;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.junit.Test;

public class Log4jTest01 {
    @Test
    public void test01(){
        /*
            log4j simple example
         */

        //Remember to load the initial configuration
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(Log4jTest01.class);


        logger.fatal("fatal level");
        logger.error("error level");
        logger.warn("warn level");
        logger.info("info level");
        logger.debug("debug level");
        logger.trace("trace level");
    }

    @Test
    public void test02(){
        /*
        Usage of configuration file
        1. review on the BasicConfigurator.configure();
            1. It creates a root logger, Logger root = Logger.getRootLogger();
            2. Root Logger adds an appender
        2. This time we would use customize configure.
            * Logger, Appender, Layout
         */
//        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(Log4jTest01.class);


        logger.fatal("fatal level");
        logger.error("error level");
        logger.warn("warn level");
        logger.info("info level");
        logger.debug("debug level");
        logger.trace("trace level");
    }

    @Test
    public void test03(){
        // Detail printing
        LogLog.setInternalDebugging(true);
        Logger logger = Logger.getLogger(Log4jTest01.class);


        logger.fatal("fatal level");
        logger.error("error level");
        logger.warn("warn level");
        logger.info("info level");
        logger.debug("debug level");
        logger.trace("trace level");
    }

    @Test
    public void test04(){
        /*
            About Layout
                PatternLayout is the most usual one
         */
        Logger logger = Logger.getLogger(Log4jTest01.class);


        logger.fatal("fatal level");
        logger.error("error level");
        logger.warn("warn level");
        logger.info("info level");
        logger.debug("debug level");
        logger.trace("trace level");
    }

    @Test
    public void test05(){
    /*
    Output to file
     */
        Logger logger = Logger.getLogger(Log4jTest01.class);
        logger.fatal("fatal level");
        logger.error("error level");
        logger.warn("warn level");
        logger.info("info level");
        logger.debug("debug level");
        logger.trace("trace level");
    }

}
