#!/bin/bash

for dir in $(ls -d1 -- */); do
	cd "$dir"
	find . -maxdepth 1 -type f | grep -q ^./pom.xml$
	if [ $? -eq 0 ]; then
		mvn test
	fi
	cd ..
done
