package lsieun.asm.core;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassChangeVersionVisitor extends ClassVisitor {
    public ClassChangeVersionVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(Opcodes.V1_7, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        if(methodVisitor!=null && !"<init>".equals(name)){
            methodVisitor= new ChangePrintAdapter(api, methodVisitor);
        }
        return methodVisitor;

    }

    private static class ChangePrintAdapter extends MethodVisitor {
        ChangePrintAdapter(int api, MethodVisitor methodVisitor) {
            super(api,methodVisitor);
        }

        /**
         * push to stack
         *
         * @param value
         */
        @Override
        public void visitLdcInsn(Object value) {
            /**
             * 修改字符串
             */
            if(value instanceof String && "Hello World".equals(value)){
                super.visitLdcInsn("Hello World modify by asm");
                return ;
            }

            if(value instanceof String && "val = 1".equals(value)){
                super.visitLdcInsn("val = 1 modify by asm");
                return ;
            }
            super.visitLdcInsn(value);
        }
    }
}
