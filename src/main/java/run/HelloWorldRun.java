package run;

import lsieun.utils.FileUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.*;

/**
 *
 */
public class HelloWorldRun {
    public static void main(String[] args) throws Exception {
        // 例子1
//        HelloWorldRun.generateInterface();
//        Class<?> clazz = Class.forName("sample.HelloWorld");
//        Object instance = clazz.newInstance();
//        System.out.println(instance);



        // 例子2
//        HelloWorldRun.generateInterface2();
//        Class<?> clazz2 = Class.forName("sample.HelloWorld");
//        Field[] declaredFields = clazz2.getDeclaredFields();
//        if (declaredFields.length > 0) {
//            System.out.println("fields:");
//            for (Field f : declaredFields) {
//                System.out.println("    " + f.getName());
//            }
//        }
//
//        Method[] declaredMethods = clazz2.getDeclaredMethods();
//        if (declaredMethods.length > 0) {
//            System.out.println("methods:");
//            for (Method m : declaredMethods) {
//                System.out.println("    " + m.getName());
//            }
//        }

        // 例子3
//        HelloWorldRun.generateClass();
//        Class<?> clazz3 = Class.forName("sample.HelloWorld");
//        System.out.println(clazz3.newInstance());


        // 例子4
//        HelloWorldRun.generateStaticCodeBlock();
//        Class<?> clazz4 = Class.forName("sample.HelloWorld");
//        System.out.println(clazz4.newInstance());
////        HelloWorldFrameTree.main(new String[1]);


        // 例子4
//        HelloWorldRun.generateIfElseCode();
//        Class<?> clazz4 = Class.forName("sample.HelloWorld");
//        Object instance = clazz4.newInstance();
//        Method test = clazz4.getMethod("test", int.class);
//        test.invoke(instance, 1);
//        test.invoke(instance, 0);

        // 例子5
//        HelloWorldRun.generateSwitchUse_Tableswitch();
//        Class<?> clazz4 = Class.forName("sample.HelloWorld");
//        Object instance = clazz4.newInstance();
//        Method test = clazz4.getMethod("test", int.class);
//        for(int i=0;i<6;i++){
//            test.invoke(instance, i);
//        }


        // 例子6
//        HelloWorldRun.generateForLoop();
//        Class<?> clazz = Class.forName("sample.HelloWorld");
//        Object obj = clazz.newInstance();
//
//        Method method = clazz.getDeclaredMethod("test");
//        method.invoke(obj);

        // 例子7
//        HelloWorldRun.generateTryCatch();
//        Class<?> clazz = Class.forName("sample.HelloWorld");
//        Object obj = clazz.newInstance();
//
//        Method method = clazz.getDeclaredMethod("test");
//        method.invoke(obj);
    }

    /**
     * 生产接口如下：
     * public interface HelloWorld {
     *
     * }
     *
     * @throws Exception
     */
    public static void generateInterface() throws Exception {
        String relative_path = "sample/HelloWorld.class";
        String filepath = FileUtils.getFilePath(relative_path);

        // (1) 生成byte[]内容
        byte[] bytes = dump();

        // (2) 保存byte[]到文件
        FileUtils.writeBytes(filepath, bytes);
    }

    public static byte[] dump() throws Exception {
        // (1) 创建ClassWriter对象
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // (2) 调用visitXxx()方法
        cw.visit(
                V1_8, //version
                ACC_PUBLIC + ACC_ABSTRACT+ACC_INTERFACE, // access
                "sample/HelloWorld", // name
                null, //signature
                "java/lang/Object", // superName
                null); // interfaces

        cw.visitEnd();

        // (3) 调用toByteArray()方法
        return cw.toByteArray();
    }

    /**
     * 预期生成接口：
     * public interface HelloWorld extends Cloneable {
     *     int LESS = -1;
     *     int EQUAL = 0;
     *     int GREATER = 1;
     *     int compareTo(Object o);
     * }
     *
     *
     * @throws Exception
     */
    public static void generateInterface2() throws Exception {
        String relative_path = "sample/HelloWorld.class";
        String filepath = FileUtils.getFilePath(relative_path);

        // (1) 生成byte[]内容
        byte[] bytes = dump2();

        // (2) 保存byte[]到文件
        FileUtils.writeBytes(filepath, bytes);
    }

    public static byte[] dump2() throws Exception {
        // (1) 创建ClassWriter对象
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // (2) 调用visitXxx()方法
        cw.visit(
                V1_8, //version
                ACC_PUBLIC + ACC_ABSTRACT+ACC_INTERFACE, // access
                "sample/HelloWorld", // name
                null, //signature
                "java/lang/Object", // superName
                new String[]{"java/lang/Cloneable"}); // interfaces
        {
            FieldVisitor fieldVisitor = cw.visitField(ACC_PUBLIC +ACC_FINAL + ACC_STATIC, "LESS", "I", null, -1);
            fieldVisitor.visitEnd();
        }
        {
            FieldVisitor fieldVisitor = cw.visitField(ACC_PUBLIC +ACC_FINAL + ACC_STATIC, "EQUAL", "I", null, 0);
            fieldVisitor.visitEnd();
        }
        {
            FieldVisitor fieldVisitor = cw.visitField(ACC_PUBLIC +ACC_FINAL + ACC_STATIC, "GREATER", "I", null, 1);
            fieldVisitor.visitEnd();
        }
        {
            MethodVisitor mv1 = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo", "(Ljava/lang/Object;)I", null, null);
            mv1.visitEnd();
        }

        cw.visitEnd();

        // (3) 调用toByteArray()方法
        return cw.toByteArray();
    }

    /**
     * 预期生成：
     * public class HelloWorld {
     *     public HelloWorld() {
     *     }
     * }
     */
    public static void generateClass() throws Exception {
        String relative_path = "sample/HelloWorld.class";
        String filepath = FileUtils.getFilePath(relative_path);

        // (1) 生成byte[]内容
        byte[] bytes = dump3();

        // (2) 保存byte[]到文件
        FileUtils.writeBytes(filepath, bytes);
    }

    public static byte[] dump3() throws Exception {
        // (1) 创建ClassWriter对象
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // (2) 调用visitXxx()方法
        cw.visit(
                V1_8, //version
                ACC_PUBLIC, // access
                "sample/HelloWorld", // name
                null, //signature
                "java/lang/Object", // superName
                null); // interfaces
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD,0);
            mv.visitMethodInsn(INVOKESPECIAL,"java/lang/Object","<init>","()V",false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1,1);
            mv.visitEnd();
        }

        cw.visitEnd();

        // (3) 调用toByteArray()方法
        return cw.toByteArray();
    }

    /**
     * 预期生成：
     * public class HelloWorld {
     *     public HelloWorld() {
     *     }
     *
     *     static {
     *         System.out.println("static code block");
     *     }
     * }
     */
    public static void generateStaticCodeBlock() throws Exception {
        String relative_path = "sample/HelloWorld.class";
        String filepath = FileUtils.getFilePath(relative_path);

        // (1) 生成byte[]内容
        byte[] bytes = dump4();

        // (2) 保存byte[]到文件
        FileUtils.writeBytes(filepath, bytes);
    }

    public static byte[] dump4() throws Exception {
        // (1) 创建ClassWriter对象
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // (2) 调用visitXxx()方法
        cw.visit(
                V1_8, //version
                ACC_PUBLIC, // access
                "sample/HelloWorld", // name
                null, //signature
                "java/lang/Object", // superName
                null); // interfaces
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD,0);
            mv.visitMethodInsn(INVOKESPECIAL,"java/lang/Object","<init>","()V",false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1,1);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("static code block");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }


        cw.visitEnd();

        // (3) 调用toByteArray()方法
        return cw.toByteArray();
    }

    /**
     * 期望生成：
     *
     * public class HelloWorld {
     *     public void test(int value) {
     *         if (value == 0) {
     *             System.out.println("value is 0");
     *         }
     *         else {
     *             System.out.println("value is not 0");
     *         }
     *     }
     * }
     *
     * @throws Exception
     */
    public static void generateIfElseCode() throws Exception {
        String relative_path = "sample/HelloWorld.class";
        String filepath = FileUtils.getFilePath(relative_path);

        // (1) 生成byte[]内容
        byte[] bytes = dump5();

        // (2) 保存byte[]到文件
        FileUtils.writeBytes(filepath, bytes);
    }

    public static byte[] dump5() throws Exception {
        // (1) 创建ClassWriter对象
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // (2) 调用visitXxx()方法
        cw.visit(
                V1_8, //version
                ACC_PUBLIC, // access
                "sample/HelloWorld", // name
                null, //signature
                "java/lang/Object", // superName
                null); // interfaces
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD,0);
            mv.visitMethodInsn(INVOKESPECIAL,"java/lang/Object","<init>","()V",false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1,1);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "test", "(I)V", null, null);
            Label elseLable = new Label();
            Label returnLable = new Label();

            mv.visitCode();
            mv.visitVarInsn(ILOAD,1);
            mv.visitJumpInsn(IFNE,elseLable);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("value is 0");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitJumpInsn(GOTO,returnLable);

            mv.visitLabel(elseLable);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("value not 0");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

            mv.visitLabel(returnLable);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }


        cw.visitEnd();

        // (3) 调用toByteArray()方法
        return cw.toByteArray();
    }

    /**
     * 期望实现：
     * public class HelloWorld {
     *     public void test(int val) {
     *         switch (val) {
     *             case 1:
     *                 System.out.println("val = 1");
     *                 break;
     *             case 2:
     *                 System.out.println("val = 2");
     *                 break;
     *             case 3:
     *                 System.out.println("val = 3");
     *                 break;
     *             case 4:
     *                 System.out.println("val = 4");
     *                 break;
     *             default:
     *                 System.out.println("val is unknown");
     *         }
     *     }
     * }
     *
     *
     * @throws Exception
     */
    public static void generateSwitchUse_Tableswitch () throws Exception {
        String relative_path = "sample/HelloWorld.class";
        String filepath = FileUtils.getFilePath(relative_path);

        // (1) 生成byte[]内容
        byte[] bytes = dump6();

        // (2) 保存byte[]到文件
        FileUtils.writeBytes(filepath, bytes);
    }

    /**
     * 实现 switch 语句可以使用 lookupswitch或 tableswitch指令
     *
     * @return
     * @throws Exception
     */
    public static byte[] dump6() throws Exception {
        // (1) 创建ClassWriter对象
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // (2) 调用visitXxx()方法
        cw.visit(
                V1_8, //version
                ACC_PUBLIC, // access
                "sample/HelloWorld", // name
                null, //signature
                "java/lang/Object", // superName
                null); // interfaces
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD,0);
            mv.visitMethodInsn(INVOKESPECIAL,"java/lang/Object","<init>","()V",false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1,1);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "test", "(I)V", null, null);
            Label caseLabel1 = new Label();
            Label caseLabel2 = new Label();
            Label caseLabel3 = new Label();
            Label caseLabel4 = new Label();
            Label defaultLabel = new Label();
            Label returnLabel = new Label();

            // 第 1 段
            mv.visitCode();
            mv.visitVarInsn(ILOAD,1);
            mv.visitTableSwitchInsn(1,4,defaultLabel,caseLabel1,caseLabel2,caseLabel3,caseLabel4);

            // 第 2 段
            mv.visitLabel(caseLabel1);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("value is 1");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitJumpInsn(GOTO,returnLabel);

            // 第 3 段
            mv.visitLabel(caseLabel2);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("value is 2");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitJumpInsn(GOTO,returnLabel);


            // 第 4 段
            mv.visitLabel(caseLabel3);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("value is 3");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitJumpInsn(GOTO,returnLabel);


            // 第 5 段
            mv.visitLabel(caseLabel4);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("value is 4");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitJumpInsn(GOTO,returnLabel);

            // 第 5 段
            mv.visitLabel(defaultLabel);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("value is unknown");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitJumpInsn(GOTO,returnLabel);

            mv.visitLabel(returnLabel);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }


        cw.visitEnd();

        // (3) 调用toByteArray()方法
        return cw.toByteArray();
    }

    /**
     * 期望生成：
     *
     * public void test(int val) {
     *         switch (val) {
     *             case 10:
     *                 System.out.println("val = 1");
     *                 break;
     *             case 20:
     *                 System.out.println("val = 2");
     *                 break;
     *             case 30:
     *                 System.out.println("val = 3");
     *                 break;
     *             case 40:
     *                 System.out.println("val = 4");
     *                 break;
     *             default:
     *                 System.out.println("val is unknown");
     *         }
     *     }
     *
     * @throws Exception
     */
    public static void generateSwitchUse_lookupswitch() throws Exception {
        String relative_path = "sample/HelloWorld.class";
        String filepath = FileUtils.getFilePath(relative_path);

        // (1) 生成byte[]内容
        byte[] bytes = dump7();

        // (2) 保存byte[]到文件
        FileUtils.writeBytes(filepath, bytes);
    }

    /**
     * 实现 switch 语句可以使用 lookupswitch 或 tableswitch 指令
     *
     * @return
     * @throws Exception
     */
    public static byte[] dump7() throws Exception {
        // (1) 创建ClassWriter对象
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // (2) 调用visitXxx()方法
        cw.visit(
                V1_8, //version
                ACC_PUBLIC, // access
                "sample/HelloWorld", // name
                null, //signature
                "java/lang/Object", // superName
                null); // interfaces
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD,0);
            mv.visitMethodInsn(INVOKESPECIAL,"java/lang/Object","<init>","()V",false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1,1);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "test", "(I)V", null, null);
            Label caseLabel1 = new Label();
            Label caseLabel2 = new Label();
            Label caseLabel3 = new Label();
            Label caseLabel4 = new Label();
            Label defaultLabel = new Label();
            Label returnLabel = new Label();

            // 第 1 段
            mv.visitCode();
            mv.visitVarInsn(ILOAD,1);
            mv.visitLookupSwitchInsn(defaultLabel,new int[]{10,20,30,40},new Label[]{caseLabel1,caseLabel2,caseLabel3,caseLabel4});

            // 第 2 段
            mv.visitLabel(caseLabel1);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("value is 10");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitJumpInsn(GOTO,returnLabel);

            // 第 3 段
            mv.visitLabel(caseLabel2);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("value is 20");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitJumpInsn(GOTO,returnLabel);


            // 第 4 段
            mv.visitLabel(caseLabel3);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("value is 30");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitJumpInsn(GOTO,returnLabel);


            // 第 5 段
            mv.visitLabel(caseLabel4);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("value is 40");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitJumpInsn(GOTO,returnLabel);

            // 第 5 段
            mv.visitLabel(defaultLabel);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("value is unknown");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitJumpInsn(GOTO,returnLabel);

            mv.visitLabel(returnLabel);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }


        cw.visitEnd();

        // (3) 调用toByteArray()方法
        return cw.toByteArray();
    }

    /**
     * 期望生成：
     * public class HelloWorld {
     *     public void test() {
     *         for (int i = 0; i < 10; i++) {
     *             System.out.println(i);
     *         }
     *     }
     * }
     *
     * @throws Exception
     */
    public static void generateForLoop() throws Exception {
        String relative_path = "sample/HelloWorld.class";
        String filepath = FileUtils.getFilePath(relative_path);

        // (1) 生成byte[]内容
        byte[] bytes = dump8();

        // (2) 保存byte[]到文件
        FileUtils.writeBytes(filepath, bytes);
    }

    /**
     * 实现 switch 语句可以使用 lookupswitch 或 tableswitch 指令
     *
     * @return
     * @throws Exception
     */
    public static byte[] dump8() throws Exception {
        // (1) 创建ClassWriter对象
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // (2) 调用visitXxx()方法
        cw.visit(
                V1_8, //version
                ACC_PUBLIC+ACC_SUPER, // access
                "sample/HelloWorld", // name
                null, //signature
                "java/lang/Object", // superName
                null); // interfaces
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD,0);
            mv.visitMethodInsn(INVOKESPECIAL,"java/lang/Object","<init>","()V",false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1,1);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "test", "()V", null, null);
            Label conditionLabel = new Label();
            Label returnLabel = new Label();

            // 第 1 段
            mv.visitCode();
            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ISTORE,1);

            // 第 2 段
            mv.visitLabel(conditionLabel);
            mv.visitVarInsn(ILOAD,1);
            mv.visitIntInsn(BIPUSH,10);
            mv.visitJumpInsn(IF_ICMPGE,returnLabel);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(ILOAD,1);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
            mv.visitIincInsn(1,1);
            mv.visitJumpInsn(GOTO,conditionLabel);

            // 第 3 段
            mv.visitLabel(returnLabel);
            mv.visitInsn(RETURN);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }


        cw.visitEnd();

        // (3) 调用toByteArray()方法
        return cw.toByteArray();
    }

    /**
     * 期望生成：
     * public class HelloWorld {
     *     public void test() {
     *         try {
     *             System.out.println("Before Sleep");
     *             Thread.sleep(1000);
     *             System.out.println("After Sleep");
     *         } catch (InterruptedException e) {
     *             e.printStackTrace();
     *         }
     *     }
     * }
     * @throws Exception
     */
    public static void generateTryCatch() throws Exception {
        String relative_path = "sample/HelloWorld.class";
        String filepath = FileUtils.getFilePath(relative_path);

        // (1) 生成byte[]内容
        byte[] bytes = dump9();

        // (2) 保存byte[]到文件
        FileUtils.writeBytes(filepath, bytes);
    }

    /**
     *
     *
     * @return
     * @throws Exception
     */
    public static byte[] dump9() throws Exception {
        // (1) 创建ClassWriter对象
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // (2) 调用visitXxx()方法
        cw.visit(
                V1_8, //version
                ACC_PUBLIC+ACC_SUPER, // access
                "sample/HelloWorld", // name
                null, //signature
                "java/lang/Object", // superName
                null); // interfaces
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD,0);
            mv.visitMethodInsn(INVOKESPECIAL,"java/lang/Object","<init>","()V",false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1,1);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "test", "()V", null, null);
            Label startLabel = new Label();
            Label endLabel = new Label();
            Label exceptionHandlerLabel = new Label();
            Label returnLabel = new Label();

            // 第 1 段
            mv.visitCode();
            // visitTryCatchBlock 可以在这里访问
            mv.visitTryCatchBlock(startLabel,endLabel,exceptionHandlerLabel,"java/lang/InterruptedException");

            // 第 2 段
            mv.visitLabel(startLabel);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("Before Sleep");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitLdcInsn(new Long(1000L));
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Thread", "sleep", "(J)V", false);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("After Sleep");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

            // 第 3 段
            mv.visitLabel(endLabel);
            mv.visitJumpInsn(GOTO, returnLabel);

            // 第 4 段
            mv.visitLabel(exceptionHandlerLabel);
            mv.visitVarInsn(ASTORE, 1);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/InterruptedException", "printStackTrace", "()V", false);

            // 第 5 段
            mv.visitLabel(returnLabel);
            mv.visitInsn(RETURN);

            // 第 6 段
            // visitTryCatchBlock 也可以在这里访问
            // mv2.visitTryCatchBlock(startLabel, endLabel, exceptionHandlerLabel, "java/lang/InterruptedException");
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
        
        cw.visitEnd();
        // (3) 调用toByteArray()方法
        return cw.toByteArray();
    }
}
