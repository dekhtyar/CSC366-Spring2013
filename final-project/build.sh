#!/bin/bash

JAVA_SRC=`find server/src -name '*.java'`
JAVA_CP="WebContent/WEB-INF/lib/wsdl4j.jar:WebContent/WEB-INF/lib/axis.jar:WebContent/WEB-INF/lib/commons-discovery-0.2.jar:WebContent/WEB-INF/lib/apache-commons-discovery.jar:WebContent/WEB-INF/lib/jaxrpc.jar:WebContent/WEB-INF/lib/apache-commons-logging.jar:WebContent/WEB-INF/lib/commons-logging.jar:WebContent/WEB-INF/lib/saaj.jar"

javac -cp $JAVA_CP -d server/bin/ $JAVA_SRC
