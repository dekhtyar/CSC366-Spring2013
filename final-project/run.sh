#!/bin/bash

bash build.sh

JAVA_CP="server/lib/db-derby-10.10.1.1-bin/lib/derby.jar:server/lib/db-derby-10.10.1.1-bin/lib/derbyLocale_cs.jar:server/lib/db-derby-10.10.1.1-bin/lib/derbyLocale_de_DE.jar:server/lib/db-derby-10.10.1.1-bin/lib/derbyLocale_es.jar:server/lib/db-derby-10.10.1.1-bin/lib/derbyLocale_fr.jar:server/lib/db-derby-10.10.1.1-bin/lib/derbyLocale_hu.jar:server/lib/db-derby-10.10.1.1-bin/lib/derbyLocale_it.jar:server/lib/db-derby-10.10.1.1-bin/lib/derbyLocale_ja_JP.jar:server/lib/db-derby-10.10.1.1-bin/lib/derbyLocale_ko_KR.jar:server/lib/db-derby-10.10.1.1-bin/lib/derbyLocale_pl.jar:server/lib/db-derby-10.10.1.1-bin/lib/derbyLocale_pt_BR.jar:server/lib/db-derby-10.10.1.1-bin/lib/derbyLocale_ru.jar:server/lib/db-derby-10.10.1.1-bin/lib/derbyLocale_zh_CN.jar:server/lib/db-derby-10.10.1.1-bin/lib/derbyLocale_zh_TW.jar:server/lib/db-derby-10.10.1.1-bin/lib/derbyclient.jar:server/lib/db-derby-10.10.1.1-bin/lib/derbynet.jar:server/lib/db-derby-10.10.1.1-bin/lib/derbyrun.jar:server/lib/db-derby-10.10.1.1-bin/lib/derbytools.jar:server:WebContent/WEB-INF/lib/axis.jar:WebContent/WEB-INF/lib/jakarta-oro-2.0.8.jar:WebContent/WEB-INF/lib/jaxrpc.jar:WebContent/WEB-INF/lib/saaj.jar:WebContent/WEB-INF/lib/wsdl4j.jar:WebContent/WEB-INF/lib/apache-commons-discovery.jar:WebContent/WEB-INF/lib/apache-commons-logging.jar"

java -cp server/bin:$JAVA_CP com.shopatron.Main
