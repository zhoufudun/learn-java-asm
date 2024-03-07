package sample;

import java.util.function.Consumer;

public class HelloWorld {

    public int add(int a, int b) {
        return a + b;
    }

    public long add(long a, long b) {
        return a + b;
    }

    public void test() {
        System.out.println("Hello World");
    }

    public void test(boolean flag) {
        if (flag) {
            System.out.println("value is true");
        } else {
            System.out.println("value is false");
        }
        return;
    }

    public void test(int val) {
        switch (val) {
            case 10:
                System.out.println("val = 1");
                break;
            case 20:
                System.out.println("val = 2");
                break;
            case 30:
                System.out.println("val = 3");
                break;
            case 40:
                System.out.println("val = 4");
                break;
            default:
                System.out.println("val is unknown");
        }
    }

    public void test2(int val) {
        for (int i = 0; i < 10; i++) {
            System.out.println("value is ="+i);
        }
    }
}