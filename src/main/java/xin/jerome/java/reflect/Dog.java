package xin.jerome.java.reflect;

/**
 * 狗对象
 *
 * @author Jerome Zhu
 * @since 2019.04.28 16:24
 */
public class Dog {
    private String name;

    public String sayName() {
        return String.format("my name is %s", name);
    }

    private String say(String word) {
        return "Hello " + word;
    }

    static {
        System.out.println("Dog initialized.");
    }
}
