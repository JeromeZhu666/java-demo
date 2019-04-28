package xin.jerome.java.reflect;

/**
 * 比较{@link ClassLoader#loadClass(String)}和{@link Class#forName(String)}的区别
 *
 * @author Jerome Zhu
 * @since 2019.04.28 21:31
 */
public class LoadDifference {

    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader classLoader = Dog.class.getClassLoader();
        Object obj = Class.forName("xin.jerome.java.reflect.Dog");
    }

}
