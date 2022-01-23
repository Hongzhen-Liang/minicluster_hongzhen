1. Final:
	1. Final_combination.sh => combination.sh => 
		1. java BroadCastClient $property $newValue
		2. mvn -Dtest=$DTest test 
		3. Error_Analyze.py
	2. Result:
		* analyze/ALL/*
	3. Visualization:
		* analyze/Visualization.py => .jpg
2. combination.sh:
	1. input: property newValue TestClass 
		* example: dfs.encrypt.data.transfer true All
	2. Control flow 
		1. First start BoardCastClient(property newValue)
		2. Then if TestClass=="ALL": mvn test 
		3. Then start Error_Analyze.py => scrach_error_property_newValue.txt, detail_error_property_newValue.txt
