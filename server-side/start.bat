@echo off
set CLASSPATH=.\classes\
set SOURCEPATH=.\src\
set LIBRARYPATH=".\lib\org.eclipse.paho.client.mqttv3-1.2.0.jar;.\lib\org.json-20180813.jar"
javac -classpath %LIBRARYPATH% -d %CLASSPATH% %SOURCEPATH%*.java
java -cp ".\classes\;.\lib\org.eclipse.paho.client.mqttv3-1.2.0.jar;.\lib\org.json-20180813.jar" Main
pause