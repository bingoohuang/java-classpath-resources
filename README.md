# cls

从classpath中寻找配置文件，谷歌关键字 `java get jvm arguments`


结论:

1. 从classpath的多个jar包内读取资源
    - 在非`-jar`方式时，可以读到多个
    - 在`-jar`方式时，只能读到main包的一个资源
1. 从classpath的多个路径读取资源，都只能读取到一个.
1. JAVA代码中，没有直接的API可以读取到通过`-cp`或者`-classpath`传入的`CLASSPATH`，可以
    - 在shell中`export CLASSPATH`, 然后`System.getenv("CLASSPATH")`，建议此种方案，简单便捷
    - [hack方式读取](https://github.com/zolyfarkas/spf4j/blob/master/spf4j-core/src/main/java/org/spf4j/unix/JVMArguments.java).

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
