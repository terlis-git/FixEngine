@echo off
set ENGINE_HOME=D:\SNAP\workspace\FixEngine
echo %ENGINE_HOME%
set CLASSPATH=%ENGINE_HOME%
set CLASSPATH=%ENGINE_HOME%\dist\SanthiFix.jar
set CLASSPATH=%CLASSPATH%;%ENGINE_HOME%\lib\log4j-1.2-api-2.8.jar
set CLASSPATH=%CLASSPATH%;%ENGINE_HOME%\lib\log4j-api-2.8.jar
set CLASSPATH=%CLASSPATH%;%ENGINE_HOME%\lib\log4j-core-2.8.jar
set CLASSPATH=%CLASSPATH%;%ENGINE_HOME%\lib\mina-core-2.0.9.jar
set CLASSPATH=%CLASSPATH%;%ENGINE_HOME%\lib\quickfixj-core-1.6.0.jar
set CLASSPATH=%CLASSPATH%;%ENGINE_HOME%\lib\quickfixj-messages-all-1.6.0.jar
set CLASSPATH=%CLASSPATH%;%ENGINE_HOME%\lib\quickfixj-messages-fix44-1.6.0.jar
set CLASSPATH=%CLASSPATH%;%ENGINE_HOME%\lib\slf4j-api-1.7.12.jar
set CLASSPATH=%CLASSPATH%;%ENGINE_HOME%\lib\slf4j-jdk14-1.7.12.jar
set CLASSPATH=%CLASSPATH%;%ENGINE_HOME%\lib\slf4j-log4j12-1.7.24.jar

echo %CLASSPATH%
java -cp %CLASSPATH% -Dlog4j.configurationFile=%ENGINE_HOME%\config\log4j.xml com.snap.engine.FixEngine config\Session.cfg
#Testing ..
TIMEOUT 100
