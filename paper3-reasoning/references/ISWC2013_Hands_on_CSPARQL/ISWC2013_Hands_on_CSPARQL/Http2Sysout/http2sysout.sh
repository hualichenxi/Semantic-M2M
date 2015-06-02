abspath=$(cd ${0%/*} && echo $PWD/${0##*/})
path_only=`dirname "$abspath"`
cd $path_only
java -Xms128m -Xmx256m -jar http2sysout-0.1.jar