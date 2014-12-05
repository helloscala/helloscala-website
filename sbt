SCRIPT_DIR=`dirname $0`
java -Dfile.encoding=UTF-8 -noverify -Xms512M -Xmx1536M -XX:MaxPermSize=386M -Xss1M -XX:+CMSClassUnloadingEnabled -jar "$SCRIPT_DIR/project/sbt-launch-0.13.7.jar" $@
