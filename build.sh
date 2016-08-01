#!/usr/bin/env bash

mvn_bin_path=/opt/app/maven/bin/mvn
war_file_name=myblog-1.0-SNAPSHOT.war
tomcat_home_path=/opt/app/tomcat-myblog
tomcat_bin_path=$tomcat_home_path/bin/catalina.sh
#webapp_name=myblog
webapp_name=ROOT
backup_folder=/root/backup

echo "Updating codes to latest"
cd /root/myblog
git pull

echo "Deleting 'out' and 'target' folder."
rm -rf out target

echo "Removing files in $backup_folder"
rm -rf $backup_folder/*

echo "Backing up blog image files to $backup_folder"
rsync -av -progress $tomcat_home_path/webapps/$webapp_name/resources $backup_folder --exclude css

echo "Changing database connection password."
sed -i -e "s/\(jdbc.password=\).*/\qwer1234sadfzxcv/" src/main/resources/jdbc.properties

echo "Running 'mvn clean package' to build codes."
$mvn_bin_path clean package -Dmaven.test.skip=true

echo "Checking if 'target/$war_file_name' exist or not ??"
if [ ! -f "target/$war_file_name" ]; then
    echo "The build war file 'target/$war_file_name' doesn't exist !! "
    exit 1
fi
echo "The build war file exists."

echo "Starting to stop tomcat server."
$tomcat_bin_path stop -force

if [ `ps aux|grep java|grep $tomcat_home_path|awk '{print $2}'` ];then
    echo "Killing tomcat process."
    ps aux|grep java|grep $tomcat_home_path|awk '{print $2}'|xargs -I {} kill -9 {}
fi

echo "Removing war and folder under tomcat home folder."
rm -rf $tomcat_home_path/webapps/$webapp_name.war
rm -rf $tomcat_home_path/webapps/$webapp_name

echo "Copying build war file to tomcat webapp folder."
cp target/$war_file_name $tomcat_home_path/webapps/$webapp_name.war

echo "Starting to startup tomcat service."
$tomcat_bin_path start

echo "Copy backing up files back to website."
sleep 10
cp -r $backup_folder/resources/* $tomcat_home_path/webapps/$webapp_name/resources/

cd ~
echo "Done for deploy!!"
