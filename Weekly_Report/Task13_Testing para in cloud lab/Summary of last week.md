1. Summary of yesterday:
	1. I show the source code of modification on minicluster, which enables the client to change all the datanodes' and namenode's parameters through socket port 9525.
	2. Illustrate the time-consuming problem on mvn test.
	3. Discuss how to use cloudlab to accelerate the mvn test process. For example, use multi-machines and write an auto testing program that would auto test several parameters.
	4. Discuss how to use log4j to record the error messages of mvn test, and discuss possible ways to analyze the message.

2. The task for this week:
	1. First try to use mvn test on cloudlab machines. Check which test would fail even without changing parameters' value during testing.(K.
	2. Modify the source code of minicluster, which enables it to broadcast its status to the client. Therefore, the client could auto change the minicluster's parameters right after its starts. I would like to use socket again first, later I would try to use the file or other means to track the status of minicluster.
	3. Change the input of Client from "parameter, newvalue" to "parameter,newvalue,testClassName", which would enable more freedom on testing.
	4. Try to build up a distributed way to test all the parameters.
	5. Goal: Work first, then performance