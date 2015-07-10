@echo off
echo ---------------------------------------------------------------------------[Creating directories...]
if not exist compiled mkdir compiled
mkdir compiled\ui\resources
mkdir compiled\ui\resources\events
echo ---------------------------------------------------------------------------[Copying resources...]
copy src\ui\resources\*.* compiled\ui\resources
copy src\ui\resources\events\*.* compiled\ui\resources\events
echo ---------------------------------------------------------------------------[Compiling...]
javac -cp lib\date4j.jar;lib\jgoodies-common-1.3.0.jar;lib\jgoodies-validation-2.4.0.jar;lib\jcalendar-1.4.jar;lib\glazedlists_java15-1.9-20111127.203634-11.jar;lib\forms-1.2.1.jar;lib\mac_widgets-0.9.5.jar;lib\opencsv-2.3.jar  -sourcepath src\ -d compiled src\digitalOrganiser\DigitalOrganiser.java
echo ---------------------------------------------------------------------------[DONE]
pause