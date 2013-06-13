#!/bin/bash

rm -rf /opt/apache-tomcat-7.0.41/webapps/shopatron/*

bash build.sh

JAVA_BIN=`find server/bin -name '*.class'`
JAVA_LIB=`find server/lib -name '*.jar'`

cp -r $JAVA_BIN WebContent/WEB-INF/classes/
cp -r $JAVA_LIB WebContent/WEB-INF/lib/

bash $CATALINA_HOME/bin/shutdown.sh

cp -r WebContent/* /opt/apache-tomcat-7.0.41/webapps/shopatron

bash $CATALINA_HOME/bin/startup.sh
