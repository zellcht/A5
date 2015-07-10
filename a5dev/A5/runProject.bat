:: Author: Samuel Jenkins
:: Date: 31/03/2012
:: Batch file to run the compiled project.
set CLASSPATH=%CD%\bin;%CD%\lib\date4j.jar;%CD%\lib\forms-1.2.1.jar;%CD%\lib\glazedlists_java15-1.9-20111127.203634-11.jar;%CD%\lib\jcalendar-1.4.jar;%CD%\lib\jgoodies-common-1.3.0.jar;%CD%\lib\jgoodies-validation-2.4.0.jar;%CD%\lib\mac_widgets-0.9.5.jar;%CD%\lib\opencsv-2.3.jar
java digitalOrganiser.DigitalOrganiser
::pause ::optional, used to check it all works. You may uncomment the pause to get it to show all the output...