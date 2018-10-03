#!/bin/bash

echo '开始.....'
#----------www.fty1.cn------
case $1 in
stop | end)
    echo "执行停止..."

    PROJECT_PWD=`pwd`
    echo "$PWD"
     # PID
    PROJECT_PID=`ps -x | grep "$JAVA_HOME/jre/bin/java" | grep "$PROJECT_PWD" | grep 'spring-boot' | awk '{print $1}'`
    if [ -n "$PROJECT_PID" ];
        then
	    echo ${PROJECT_PID}
	    kill -9 ${PROJECT_PID}
	    echo "已停止。"
    fi
    ;;

info)
    echo "执行信息..."
    PROJECT_PWD=`pwd`
    echo "$PWD"
    PROJECT_PID=`ps -x | grep "$JAVA_HOME/jre/bin/java"| grep "$PROJECT_PWD" | grep 'spring-boot' | awk '{print $1}'`
    echo ${PROJECT_PID}
     echo "已结束。"
    ;;
*)
    echo "执行部署"

    #-maven编译
    mvn clean
    mvn package -Dmaven.test.skip=true

    PROJECT_PWD=`pwd`
    echo "$PWD"

    # PID
    PROJECT_PID=`ps -x | grep "$JAVA_HOME/jre/bin/java" | grep "$PROJECT_PWD" | grep 'spring-boot' | awk '{print $1}'`
    if [ -n "$PROJECT_PID" ];
        then
	    echo ${PROJECT_PID}
	    kill -9 ${PROJECT_PID}
    fi

    #-spring-boot:run执行
    mvn spring-boot:run >log.log 2>&1 &
    #----------www.fty1.cn------
    echo '已部署。'
    tail -f log.log
    ;;
esac

