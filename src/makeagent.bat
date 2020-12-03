javac -classpath ../asm-7.2.jar -encoding utf-8 Agent.java MemClassVisitor.java MemoryTransformer.java  MyMethodVisitor.java Print.java
jar -cvfm agent.jar MANIFEST.MF Agent.class MemoryTransformer.class MemClassVisitor.class  MyMethodVisitor.class Print.class
del Agent.class Agent.class MemoryTransformer.class MemClassVisitor.class  MyMethodVisitor.class Print.class

javac -encoding utf-8 Main.java
java  -javaagent:agent.jar  -Dfile.encoding=UTF-8 -classpath C:\Users\Dell\Desktop\BytePlug\asm-7.2.jar;. Main
pause
