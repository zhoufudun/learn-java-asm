package run;

import lsieun.utils.FileUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.Field;
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
        HelloWorldRun.generateInterface2();
        Class<?> clazz2 = Class.forName("sample.HelloWorld");
        Field[] declaredFields = clazz2.getDeclaredFields();
        if (declaredFields.length > 0) {
            System.out.println("fields:");
            for (Field f : declaredFields) {
                System.out.println("    " + f.getName());
            }
        }

        Method[] declaredMethods = clazz2.getDeclaredMethods();
        if (declaredMethods.length > 0) {
            System.out.println("methods:");
            for (Method m : declaredMethods) {
                System.out.println("    " + m.getName());
            }
        }
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
}
