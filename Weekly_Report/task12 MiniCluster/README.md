# About Using the MiniDFSCluster
0. Structure: https://www.cnblogs.com/jstarseven/p/7682221.html
1. Hadoop: How to unit test FileSystem: https://stackoverflow.com/questions/8308828/hadoop-how-to-unit-test-filesystem

2. Map:
	1. MiniDFSCluster cluster = new MiniDFSCluster.Builder(conf).build();
		1. dataNodes-> datanode -> DataNode.java:
			```
			List<DataNode> datanodes = cluster.getDataNodes();
			DataNode datanode = datanodes.get(0);
			```
		2. public NameNode nameNode -> NameNode.java
	2. FileSystem fs = FileSystem.get(localConf);

3. FileSystem:
	1. write
		```
		Path TEST_PATH = new Path("/non-encrypted-file");
		OutputStream out = null;
		if (!fs.exists(TEST_PATH)) {
		  out = fs.create(TEST_PATH);
		} else {
		  out = fs.append(TEST_PATH);
		}
		out.write(PLAIN_TEXT.getBytes());
		out.close();
		```
		
4. combination.sh:
	1. input: property newValue TestClass 
		* example: dfs.encrypt.data.transfer true All
	2. Control flow 
		1. First start BoardCastClient(property newValue)
		2. Then if TestClass=="ALL": mvn test 
		3. Then start Error_Analyze.py => scrach_error_property_newValue.txt, detail_error_property_newValue.txt
	3. Final:
		1. Final_combination.sh => combination.sh => 
			1. java BroadCastClient $property $newValue
			2. mvn -Dtest=$DTest test 
			3. Error_Analyze.py
		2. Result:
			* analyze/ALL/*
		3. Visualization:
			* analyze/Visualization.py => .jpg
		