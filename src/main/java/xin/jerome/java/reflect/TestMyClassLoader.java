package xin.jerome.java.reflect;

/**
 * 测试自定义{@link ClassLoader}
 *
 * @author Jerome Zhu
 * @since 2019.04.28 17:57
 */
public class TestMyClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader myClassLoader = new MyClassLoader("C:/Users/Administrator/Desktop/","Dog");
        Object dog = myClassLoader.loadClass("Dog");
        System.out.println(myClassLoader.getParent());
        // AppClassLoader 扫描的路径   java.class.path
        System.out.println(myClassLoader.getParent().getParent());
        // ExtClassLoader 扫描的路径   java.ext.dirs
        System.out.println(myClassLoader.getParent().getParent().getParent());
        ((Class) dog).newInstance();
    }



}
