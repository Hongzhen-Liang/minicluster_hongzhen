#!/bin/bash

# use for eliminate \r wrote by windows system
sed -i 's/\r//' analyze/ToTestProperty.txt
sed -i 's/\r//' analyze/AlreadyTestProperty.txt

cat analyze/ToTestProperty.txt | while read line
do
	array=(${line// / })
	./combination.sh ${array[0]} ${array[1]}
done
