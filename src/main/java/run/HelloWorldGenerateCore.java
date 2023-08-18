package run;

import lsieun.utils.FileUtils;
import org.objectweb.asm.*;
import sample.MyTag;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;

import static org.objectweb.asm.Opcodes.*;

public class HelloWorldGenerateCore {

    public static void main(String[] args) throws Exception {
        // 1
//        test1();


        // 2
//        generateField();
//        Class<?> clazz3 = Class.forName("sample.HelloWorld");
//        Field[] declaredFields = clazz3.getDeclaredFields();
//        if (declaredFields.length > 0) {
//            System.out.println("fields:");
//            for (Field f : declaredFields) {
//                System.out.println("    " + f.getName());
//            }
//        }

        // 3
//        generateAnnotation();
//        Class<?> clazz3 = Class.forName("sample.HelloWorld");
//        Field[] declaredFields = clazz3.getDeclaredFields();
//        if (declaredFields.length > 0) {
//            System.out.println("fields:");
//            for (Field nameField : declaredFields) {
//                System.out.println("    " + nameField.getName());
//                // 获取字段上的注解对象
//                Annotation[] annotations = nameField.getDeclaredAnnotations();
//
//                // 获取注解的属性值
//                for (Annotation annotation : annotations) {
//                    if (annotation instanceof MyTag) {
//                        MyTag myTag = (MyTag) annotation;
//                        System.out.println("Name value: " + myTag.name());
//                        System.out.println("age value: " + myTag.age());
//                    }
//                }
//            }
//        }

        // 例子4：
//        generateSuper();
//        Class<?> clazz = Class.forName("sample.HelloWorld");
//        System.out.println(clazz);
//        HelloWorldFrameCore.main(new String[0]); // 打印以下本地变量表的操作数栈变化过程
//

        // 例子5：
        generateStaticCodeBlock();
        Class<?> clazz = Class.forName("sample.HelloWorld");
        System.out.println(clazz);
        HelloWorldFrameCore.main(new String[0]); // 打印以下本地变量表的操作数栈变化过程


    }

    public static void test1() throws Exception {
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
        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, "sample/HelloWorld",
                null, "java/lang/Object", null);

        {
            MethodVisitor mv1 = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv1.visitCode();
            mv1.visitVarInsn(ALOAD, 0);
            mv1.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv1.visitInsn(RETURN);
            mv1.visitMaxs(1, 1);
            mv1.visitEnd();
        }

        {
            MethodVisitor mv2 = cw.visitMethod(ACC_PUBLIC, "test", "()V", null, null);
            mv2.visitCode();
            mv2.visitInsn(NOP);
            mv2.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv2.visitInsn(NOP);
            mv2.visitLdcInsn("Hello World");
            mv2.visitInsn(NOP);
            mv2.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv2.visitInsn(NOP);
            mv2.visitInsn(RETURN);
            mv2.visitMaxs(2, 1);
            mv2.visitEnd();
        }
        cw.visitEnd();

        // (3) 调用toByteArray()方法
        return cw.toByteArray();
    }

    /**
     * 期望生成：
     * public interface HelloWorld {
     * int intValue = 100;
     * String strValue = "ABC";
     * }
     *
     * @throws Exception
     */
    public static void generateField() throws Exception {
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
        cw.visit(V1_8, ACC_PUBLIC | ACC_INTERFACE | ACC_ABSTRACT, "sample/HelloWorld",
                null, "java/lang/Object", null);

        {
            FieldVisitor fieldVisitor = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "initValue", "I", null, 100);
            fieldVisitor.visitEnd();
        }
        {
            FieldVisitor fieldVisitor = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "StrValue", "Ljava/lang/String;", null, "ABC");
            fieldVisitor.visitEnd();
        }

        cw.visitEnd();

        // (3) 调用toByteArray()方法
        return cw.toByteArray();
    }


    /**
     * 期望生成：
     * public interface HelloWorld {
     *      @MyTag(name = "tomcat", age = 10)
     *      int intValue = 100;
     * }
     */
    public static void generateAnnotation() throws Exception {
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

        // (2) 调用 visitXxx() 方法
        cw.visit(V1_8, ACC_PUBLIC | ACC_ABSTRACT | ACC_INTERFACE,
                "sample/HelloWorld", null, "java/lang/Object", null);

        {
            /**
             * 访问顺序如下：
             *
             * (
             *      visitAnnotation |
             *      visitTypeAnnotation |
             *      visitAttribute
             * )*
             * visitEnd
             */
            FieldVisitor fieldVisitor = cw.visitField(ACC_PUBLIC | ACC_FINAL | ACC_STATIC, "intValue", "I", null, 100);
            {
                AnnotationVisitor annotation = fieldVisitor.visitAnnotation("Lsample/MyTag;", false);
                annotation.visit("name", "tomcat");
                annotation.visit("age", 10);
                annotation.visitEnd();
            }
            fieldVisitor.visitEnd();
        }


        cw.visitEnd();

        // (3) 调用toByteArray()方法
        return cw.toByteArray();
    }

    /**
     * 期望生成：
     * public class HelloWorld {
     *     public HelloWorld() {
     *         super();
     *     }
     * }
     */
    public static void generateSuper() throws Exception {
        String relative_path = "sample/HelloWorld.class";
        String filepath = FileUtils.getFilePath(relative_path);
        // (1) 生成byte[]内容
        byte[] bytes = dump4();

        // (2) 保存byte[]到文件
        FileUtils.writeBytes(filepath, bytes);
    }

    public static byte[] dump4() throws Exception {
        // (1) 创建 ClassWriter 对象
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // (2) 调用 visitXxx() 方法
        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, "sample/HelloWorld", null, "java/lang/Object", null);

        {
            MethodVisitor mv1 = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv1.visitCode();
            mv1.visitVarInsn(ALOAD, 0);
            // 这句一定要，虽然生成的.class文件看起来没问题，但是实例化会报错
            mv1.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv1.visitInsn(RETURN);
            mv1.visitMaxs(1, 1);
            mv1.visitEnd();
        }

        cw.visitEnd();

        // (3) 调用 toByteArray() 方法
        return cw.toByteArray();
    }

    /**
     * 期望生成：
     public class HelloWorld {
        static {
            System.out.println("class initialization method");
        }
     }
     */
    public static void generateStaticCodeBlock() throws Exception {
        String relative_path = "sample/HelloWorld.class";
        String filepath = FileUtils.getFilePath(relative_path);
        // (1) 生成byte[]内容
        byte[] bytes = dump5();

        // (2) 保存byte[]到文件
        FileUtils.writeBytes(filepath, bytes);
    }

    public static byte[] dump5() throws Exception {
        // (1) 创建 ClassWriter 对象
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // (2) 调用 visitXxx() 方法
        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, "sample/HelloWorld", null, "java/lang/Object", null);

        {
            MethodVisitor mv1 = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv1.visitCode();
            mv1.visitVarInsn(ALOAD, 0);
            // 这句一定要，虽然生成的.class文件看起来没问题，但是实例化会报错
            mv1.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv1.visitInsn(RETURN);
            mv1.visitMaxs(1, 1);
            mv1.visitEnd();
        }

            System.out.println("");
        {
            MethodVisitor mv2 = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
            mv2.visitCode();
            mv2.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv2.visitLdcInsn("class initialization method");
            mv2.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv2.visitInsn(RETURN);
            mv2.visitMaxs(2, 0);
            mv2.visitEnd();
        }

        cw.visitEnd();

        // (3) 调用 toByteArray() 方法
        return cw.toByteArray();
    }
}