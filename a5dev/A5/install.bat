:: Author: Samuel Jenkins
:: Date: 01/04/2012
:: Installation file for the Group7 project.
:: Installs and compiles all the files on the users desktop.
mkdir %USERPROFILE%\Desktop\cs235Group7a5
mkdir %USERPROFILE%\Desktop\cs235Group7a5\bin
mkdir %USERPROFILE%\Desktop\cs235Group7a5\bin\ui\resources
mkdir %USERPROFILE%\Desktop\cs235Group7a5\bin\ui\resources\events
mkdir %USERPROFILE%\Desktop\cs235Group7a5\lib
set CLASSPATH=%CD%\src;%CD%\lib\date4j.jar;%CD%\lib\forms-1.2.1.jar;%CD%\lib\glazedlists_java15-1.9-20111127.203634-11.jar;%CD%\lib\jcalendar-1.4.jar;%CD%\lib\jgoodies-common-1.3.0.jar;%CD%\lib\jgoodies-validation-2.4.0.jar;%CD%\lib\mac_widgets-0.9.5.jar;%CD%\lib\opencsv-2.3.jar
javac src\addressBook\*.java src\dateAndTime\*.java src\digitalOrganiser\*.java src\events\*.java src\file\*.java src\testingFramework\*.java src\common\*.java src\ui\*.java src\views\*.java -d %USERPROFILE%\Desktop\cs235Group7a5\bin
copy src\ui\resources\* %USERPROFILE%\Desktop\cs235Group7a5\bin\ui\resources
copy src\ui\resources\events\* %USERPROFILE%\Desktop\cs235Group7a5\bin\ui\resources\events
copy lib\*.jar %USERPROFILE%\Desktop\cs235Group7a5\lib
copy bin\*.csv %USERPROFILE%\Desktop\cs235Group7a5
copy EventTestFiles\*.csv %USERPROFILE%\Desktop\cs235Group7a5
copy ContactTestFiles\*.csv %USERPROFILE%\Desktop\cs235Group7a5
copy runProject.bat %USERPROFILE%\Desktop\cs235Group7a5
::pause ::optional, used to check it all works. You may uncomment the pause to get it to show all the output...