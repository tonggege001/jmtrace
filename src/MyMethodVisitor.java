import com.sun.org.apache.xpath.internal.compiler.OpCodes;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MyMethodVisitor extends MethodVisitor {

    public MyMethodVisitor(int asm7, MethodVisitor methodVisitor){
        super(asm7, methodVisitor);
    }

    public void visitFieldInsn(int opcode, String owner, String name, String descriptor){
        if(mv == null){
            return;
        }

        if (opcode == Opcodes.GETSTATIC){
            visitGetStaticTrace(owner, name, descriptor);
        }
        else if(opcode == Opcodes.PUTSTATIC){
            visitPutStaticTrace(owner, name, descriptor);
        }
        else if(opcode == Opcodes.GETFIELD){
            VisitGetFieldTrace(owner, name, descriptor);
        }
        else if(opcode == Opcodes.PUTFIELD){
            visitPutFieldTrace(owner, name, descriptor);
        }

        super.visitFieldInsn(opcode, owner, name, descriptor);
    }

    public void visitInsn(int opcode){
        if(mv == null){
            return;
        }
        if(opcode >= Opcodes.IALOAD && opcode <= Opcodes.SALOAD){
            visitXALoad(opcode);
        }
        else if (opcode >= Opcodes.IASTORE && opcode <= Opcodes.SASTORE){
            visitXAStore(opcode);
        }

        super.visitInsn(opcode);
    }

    public void visitGetStaticTrace(String owner, String name, String descriptor){
        mv.visitLdcInsn(owner);
        mv.visitLdcInsn(name);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, Print.clname, "PrintGetStatic", "(Ljava/lang/String;Ljava/lang/String;)V", false);
    }

    public void visitPutStaticTrace(String owner, String name, String descriptor){
        mv.visitLdcInsn(owner);
        // ..., str
        mv.visitLdcInsn(name);
        // ..., str, str
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, Print.clname, "PrintPutStatic",
                "(Ljava/lang/String;Ljava/lang/String;)V", false);
    }

    public void VisitGetFieldTrace(String owner, String name, String descriptor){
        mv.visitInsn(Opcodes.DUP);
        mv.visitLdcInsn(name);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, Print.clname, "PrintGetField", "(Ljava/lang/Object;Ljava/lang/String;)V", false);
    }

    public void visitPutFieldTrace(String owner, String name, String descriptor){
        if (descriptor.equals("J") || descriptor.equals("D")) {
            mv.visitInsn(Opcodes.DUP2_X1);
            mv.visitInsn(Opcodes.POP2);

        } else {
            mv.visitInsn(Opcodes.SWAP);
        }

        mv.visitInsn(Opcodes.DUP);
        // ..., val..., obj, obj
        mv.visitLdcInsn(name);
        // ..., val..., obj, obj, str
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, Print.clname, "PrintPutField",
                "(Ljava/lang/Object;Ljava/lang/String;)V", false);

        if (descriptor.equals("J") || descriptor.equals("D")) {
            mv.visitInsn(Opcodes.DUP_X2);
            mv.visitInsn(Opcodes.POP);
        }
        else{
            mv.visitInsn(Opcodes.SWAP);
        }
    }

    public void visitXALoad(int opcode){
        mv.visitInsn(Opcodes.DUP2);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, Print.clname, "PrintXALoad", "(Ljava/lang/Object;I)V", false);
    }

    public void visitXAStore(int opcode){
        if(opcode == Opcodes.LASTORE || opcode == Opcodes.DASTORE){
            mv.visitInsn(Opcodes.DUP2_X2);
            mv.visitInsn(Opcodes.POP2);
        }
        else{
            mv.visitInsn(Opcodes.DUP_X2);
            mv.visitInsn(Opcodes.POP);
        }
        mv.visitInsn(Opcodes.DUP2);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, Print.clname, "PrintXAStore",
                "(Ljava/lang/Object;I)V", false);
        if(opcode == Opcodes.LASTORE || opcode == Opcodes.DASTORE){
            mv.visitInsn(Opcodes.DUP2_X2);
            mv.visitInsn(Opcodes.POP2);
        }
        else{
            mv.visitInsn(Opcodes.DUP2_X1);
            mv.visitInsn(Opcodes.POP2);
        }
    }

}
