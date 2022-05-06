# Task 11: log4j
1. log4j
	* url: https://www.bilibili.com/video/BV1Mb4y1Z74W?p=46
	1. In order to track the route of minicluster, I need this tool to documented all the intermedia data.
2. hadoop custumize log4j:
	1. log4j.properties
	```
	log4j.logger.MyLog=DEBUG,E
	log4j.appender.E = org.apache.log4j.DailyRollingFileAppender
	log4j.appender.E.File =${hadoop.log.dir}/HongzhenDebug.log
	log4j.appender.E.Append = false
	log4j.appender.E.Threshold = INFO 
	log4j.appender.E.layout = org.apache.log4j.PatternLayout
	log4j.appender.E.layout.ConversionPattern = %-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
	log4j.additivity.MyLog=false
	```
	2. code
	```
	Log mylog = LogFactory.getLog("MyLog");
	mylog.info("getTransferSocketSendBufferSize was called");
	```