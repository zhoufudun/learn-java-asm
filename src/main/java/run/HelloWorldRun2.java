package run;

import lsieun.utils.FileUtils;
import org.objectweb.asm.ClassReader;

import java.util.Arrays;

public class HelloWorldRun2 {
    public static void main(String[] args) throws Exception {
        test1();

    }
    /**
     * 假如，有如下一个类：
     * import java.io.Serializable;
     *
     * public class HelloWorld extends Exception implements Serializable, Cloneable {
     *
     * }
     */

    public static void test1(){
        String relative_path = "sample/zfd/HelloWorld1.class";
        String filepath = FileUtils.getFilePath(relative_path);
        byte[] bytes = FileUtils.readBytes(filepath);

        //（1）构建 ClassReader
        ClassReader cr = new ClassReader(bytes);

        // (2) 调用 getXxx() 方法
        int access = cr.getAccess();
        System.out.println("access: " + access);

        String className = cr.getClassName();
        System.out.println("className: " + className);

        String superName = cr.getSuperName();
        System.out.println("superName: " + superName);

        String[] interfaces = cr.getInterfaces();
        System.out.println("interfaces: " + Arrays.toString(interfaces));

    }
}
