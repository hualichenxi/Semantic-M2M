abspath=$(cd ${0%/*} && echo $PWD/${0##*/})
path_only=`dirname "$abspath"`
cd $path_only
java -Xms512m -Xmx1g -jar rsp-services-csparql-0.3.2.3.jar