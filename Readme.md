# 环境要求

JDK 1.8

# 编译生成jar然后运行方法  
cd BytePlug/src

makeagent.bat

# 编译后使用
运行批处理脚本后在src下产生一个agent.jar，使用方法是 java -javaagent:agent.jar -classpath [jar file or class dir] [javaclass file]



