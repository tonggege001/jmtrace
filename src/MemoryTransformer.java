import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;


public class MemoryTransformer implements ClassFileTransformer {
    private String classNameKeyword;

    public MemoryTransformer(String classNameKeyword) {
        this.classNameKeyword = classNameKeyword;
    }

    public byte[] transform(ClassLoader classLoader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if(className.startsWith( "jdk") || className.startsWith("java") ||
    className.startsWith("sun") || className.startsWith("com/intellij")) return null;

        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);

        ClassVisitor visitor = new MemClassVisitor(classWriter);

        classReader.accept(visitor, 0);
        return classWriter.toByteArray();
    }

    /*
    下面注释的部分是用javassist尝试，发现这个编程题用javassist没法做（也可能是我没找到API）
     */
//    private void transformMethod(CtMethod ctMethod, CtClass ctClass) throws Exception {
//        if((ctMethod.getModifiers()& Modifier.ABSTRACT)>0){
//            return;
//        }
//
//        System.out.println("transformMethod");
//        String methodName = ctMethod.getName();
//        System.out.println("Method Name: "+methodName);
//        String helloworld = "Hello world, Agent";
//
//        //实例化新的方法
//        String newMethodName = methodName + "$impl";
//        ctMethod.setName(newMethodName);
//        CtMethod newMethod = CtNewMethod.copy(ctMethod,methodName, ctClass, null);
//        StringBuilder bodyStr = new StringBuilder();
//
//        //拼接新方法
//        bodyStr.append("{");
//
//        //返回类型
//        CtClass retType = ctMethod.getReturnType();
//
//        boolean hasReturnValue = (CtClass.voidType != retType);
//
//        bodyStr.append("\nSystem.out.println(\"method name: ").append(ctMethod.getLongName()).append("\");\n");
//
//        if (hasReturnValue) {
//            bodyStr.append("\n").append("return  ($r)"+newMethodName + "($$);");
//        }
//        bodyStr.append("}");
//        newMethod.setBody(bodyStr.toString());
//        ctClass.addMethod(newMethod);
//
//
//
//
//
//    }

//    private void transformMethod(CtMethod ctMethod, CtClass ctClass) throws Exception {
//        if((ctMethod.getModifiers()& Modifier.ABSTRACT)>0){
//            return;
//        }
//
//        System.out.println("transformMethod");
//        String methodName = ctMethod.getName();
//        System.out.println("Method Name: "+methodName);
//
//        CodeAttribute ca = ctMethod.getMethodInfo().getCodeAttribute();
//        CodeIterator i = ca.iterator();
//        while(i.hasNext()){
//            i.
//            i.next();
//        }
//
//
//        String helloworld = "Hello world, Agent";
//
//        //实例化新的方法
//        String newMethodName = methodName + "$impl";
//        ctMethod.setName(newMethodName);
//        CtMethod newMethod = CtNewMethod.copy(ctMethod,methodName, ctClass, null);
//        StringBuilder bodyStr = new StringBuilder();
//
//        //拼接新方法
//        bodyStr.append("{");
//
//        //返回类型
//        CtClass retType = ctMethod.getReturnType();
//
//        boolean hasReturnValue = (CtClass.voidType != retType);
//
//        bodyStr.append("\nSystem.out.println(\"method name: ").append(ctMethod.getLongName()).append("\");\n");
//
//        if (hasReturnValue) {
//            bodyStr.append("\n").append("return  ($r)"+newMethodName + "($$);");
//        }
//        bodyStr.append("}");
//        newMethod.setBody(bodyStr.toString());
//        ctClass.addMethod(newMethod);
//
//
//
//
//
//    }

}
