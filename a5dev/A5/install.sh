# Author: Samuel Jenkins
# Date: 01/04/2012
# Installation file for the Group7 project.
# Installs and compiles all the files on the users desktop.
mkdir ~/Desktop/cs235Group7a5
mkdir ~/Desktop/cs235Group7a5/bin 
mkdir ~/Desktop/cs235Group7a5/bin/ui/resources 
mkdir ~/Desktop/cs235Group7a5/bin/ui/resources/events 
mkdir ~/Desktop/cs235Group7a5/lib 
export CLASSPATH="./src:./lib/date4j.jar:./lib/forms-1.2.1.jar:./lib/glazedlists_java15-1.9-20111127.203634-11.jar:./lib/jcalendar-1.4.jar:./lib/jgoodies-common-1.3.0.jar:./lib/jgoodies-validation-2.4.0.jar:./lib/mac_widgets-0.9.5.jar:./lib/opencsv-2.3.jar"
javac src/addressBook/*.java src/dateAndTime/*.java src/digitalOrganiser/*.java src/events/*.java src/file/*.java src/common/*.java src/common/*.java src/ui/*.java src/views/*.java -d ~/Desktop/cs235Group7a5/bin
cp -r src/ui/resources/* ~/Desktop/cs235Group7a5/bin/ui/resources 
cp -r src/ui/resources/events/* ~/Desktop/cs235Group7a5/bin/ui/resources/events 
cp -r ./lib/*.jar ~/Desktop/cs235Group7a5/lib 
cp -r ./bin/*.csv ~/Desktop/cs235Group7a5 
cp -r ./EventTestFiles/*.csv ~/Desktop/cs235Group7a5 
cp -r ./ContactTestFiles/*.csv ~/Desktop/cs235Group7a5 
cp -r runProject.sh ~/Desktop/cs235Group7a5 
#