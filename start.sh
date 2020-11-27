#!/bin/bash

function add_java_classpath() {
  local LOCAL1=$1
  if [ "$OSTYPE" == cygwin ]; then
    LOCAL1="$(cygpath -C ANSI -w $LOCAL1)"
  fi
  if [ -z "$JAVA_CLASSPATH" ]; then
    JAVA_CLASSPATH="$LOCAL1"
  elif [ "$OSTYPE" != cygwin ]; then
    JAVA_CLASSPATH="${JAVA_CLASSPATH}:$LOCAL1"
  else
    JAVA_CLASSPATH="${JAVA_CLASSPATH};$LOCAL1"
  fi
}

add_java_classpath .
add_java_classpath aa.jar
add_java_classpath target/cls-1.0.jar
add_java_classpath /Users/bingoobjca/Downloads

export JAVA_CLASSPATH
echo ${JAVA_CLASSPATH}

java -cp "${JAVA_CLASSPATH}" -jar target/cls-1.0-exec.jar

add_java_classpath target/cls-1.0-exec.jar
export JAVA_CLASSPATH
echo ${JAVA_CLASSPATH}

java -cp "${JAVA_CLASSPATH}" cn.bjh.Cls

# Thanks https://stackoverflow.com/a/41314590/14077979
