# cls

从classpath中寻找配置文件，谷歌关键字 `java get jvm arguments`


结论:

1. 从classpath中，

```bash
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
```

执行 

1. `mvn clean package`
1. `bash start.sh`

```bash
🕙[2020-11-27 00:27:56] ❯ bash start.sh
❯ bash start.sh
.:aa.jar:target/cls-1.0.jar:/Users/bingoobjca/Downloads
env JAVA_CLASSPATH:.:aa.jar:target/cls-1.0.jar:/Users/bingoobjca/Downloads
split JAVA_CLASSPATH:[., aa.jar, target/cls-1.0.jar, /Users/bingoobjca/Downloads]
System.getProperty java.class.path: target/cls-1.0-exec.jar
getProtectionDomain: /Users/bingoobjca/bjcagit/cls/target/cls-1.0-exec.jar
find classpath*:aa/a.txt, total 1
classpath:URL [jar:file:/Users/bingoobjca/bjcagit/cls/target/cls-1.0-exec.jar!/aa/a.txt]
find file:aa/a.txt, total 1
file:URL [file:aa/a.txt]
ClassGraph: [file:/Users/bingoobjca/bjcagit/cls/target/cls-1.0-exec.jar]
MXBean InputArguments: []
spf4j JVMArguments: JVMArguments{arguments=[java, -cp, .:aa.jar:target/cls-1.0.jar:/Users/bingoobjca/Downloads, -jar, target/cls-1.0-exec.jar]}

.:aa.jar:target/cls-1.0.jar:/Users/bingoobjca/Downloads:target/cls-1.0-exec.jar
env JAVA_CLASSPATH:.:aa.jar:target/cls-1.0.jar:/Users/bingoobjca/Downloads:target/cls-1.0-exec.jar
split JAVA_CLASSPATH:[., aa.jar, target/cls-1.0.jar, /Users/bingoobjca/Downloads, target/cls-1.0-exec.jar]
System.getProperty java.class.path: .:aa.jar:target/cls-1.0.jar:/Users/bingoobjca/Downloads:target/cls-1.0-exec.jar
getProtectionDomain: /Users/bingoobjca/bjcagit/cls/target/cls-1.0.jar
find classpath*:aa/a.txt, total 3
classpath:URL [file:/Users/bingoobjca/bjcagit/cls/aa/a.txt]
classpath:URL [jar:file:/Users/bingoobjca/bjcagit/cls/target/cls-1.0.jar!/aa/a.txt]
classpath:URL [jar:file:/Users/bingoobjca/bjcagit/cls/target/cls-1.0-exec.jar!/aa/a.txt]
find file:aa/a.txt, total 1
file:URL [file:aa/a.txt]
ClassGraph: [file:/Users/bingoobjca/bjcagit/cls/, file:/Users/bingoobjca/bjcagit/cls/target/cls-1.0.jar, file:/Users/bingoobjca/Downloads/, file:/Users/bingoobjca/bjcagit/cls/target/cls-1.0-exec.jar]
MXBean InputArguments: []
spf4j JVMArguments: JVMArguments{arguments=[java, -cp, .:aa.jar:target/cls-1.0.jar:/Users/bingoobjca/Downloads:target/cls-1.0-exec.jar, cn.bjh.Cls]}
```
