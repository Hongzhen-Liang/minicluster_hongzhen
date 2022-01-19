#!/bin/bash

mvn package -Dmaven.compile.fork=true -Pdist -DskipTests -Dmaven.javadoc.skip=true
