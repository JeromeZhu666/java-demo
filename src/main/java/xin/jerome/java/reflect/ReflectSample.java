package xin.jerome.java.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射的例子
 */
public class ReflectSample {

    public static void main(String[] args) throws Exception {
        // 获取该类的字节码文件对象
        Class rd = Class.forName("xin.jerome.java.reflect.Dog");
        // 创建该类的实例
        Dog dog = (Dog) rd.newInstance();
        System.out.println(String.format("Class name is %s", rd.getName()));
        // 获取私有方法
        Method say = rd.getDeclaredMethod("say", String.class);
        // 设置访问性
        say.setAccessible(true);
        System.out.println(say.invoke(dog, "gg"));

        Method sayName = rd.getMethod("sayName");
        System.out.println(sayName.invoke(dog));
        Field name = rd.getDeclaredField("name");
        name.setAccessible(true);
        name.set(dog,"jack");
        System.out.println(sayName.invoke(dog));
    }

}
