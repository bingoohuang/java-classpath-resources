package cn.bjh;

import com.google.common.base.Splitter;
import io.github.classgraph.ClassGraph;
import lombok.SneakyThrows;
import lombok.val;
import org.spf4j.unix.JVMArguments;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.util.List;

/**
 * 演示从类路径查找所有a.txt的文件.
 *
 * @author bingoobjca
 */
public class Cls {
  @SneakyThrows
  public static void main(final String[] args) {
    String javaClasspath = System.getenv("JAVA_CLASSPATH");
    System.out.println("env JAVA_CLASSPATH:" + javaClasspath);
    System.out.println("split JAVA_CLASSPATH:" + Splitter.on(File.pathSeparatorChar).splitToList(javaClasspath));
    System.out.println("System.getProperty java.class.path: " + System.getProperty("java.class.path"));

    System.out.println("getProtectionDomain: " + new File(Cls.class.getProtectionDomain().getCodeSource().getLocation().getPath()));

    val resolver = new PathMatchingResourcePatternResolver();
    val resources = resolver.getResources("classpath*:aa/a.txt");
    System.out.println("find classpath*:aa/a.txt, total " + resources.length);
    for (val resource : resources) {
      System.out.println("classpath:" + resource);
    }

    val files = resolver.getResources("file:aa/a.txt");
    System.out.println("find file:aa/a.txt, total " + files.length);
    for (val resource : files) {
      System.out.println("file:" +resource);
    }

    List<URI> classpath = new ClassGraph().getClasspathURIs();
    System.out.println("ClassGraph: " + classpath);

    // https://coderanch.com/t/588249/java/jvm-args-passed-command-line
    // As for the original question, the behaviour of RuntimeMXBean.getInputArguments() is
    // JVM-implementation specific. So, I would not recommend relying on it too much. Usually,
    // application code does not need (or care) about what JVM arguments were supplied to the
    // runtime, and for good reason. However, if you do want to have the application behave
    // differently in say, different memory conditions etc., there are ways to get those settings
    // from Runtime, instead of getting JVM arguments (and parsing them etc.).
    //
    // IMHO, try to get what you want directly from Runtime, instead of worrying too much about the
    // exact JVM arguments supplied via command-line.
    System.out.println(
        "MXBean InputArguments: " + ManagementFactory.getRuntimeMXBean().getInputArguments());

    // https://github.com/zolyfarkas/spf4j/blob/master/spf4j-core/src/main/java/org/spf4j/unix/JVMArguments.java
    JVMArguments current = JVMArguments.current();
    System.out.println("spf4j JVMArguments: " + current);
  }
}
