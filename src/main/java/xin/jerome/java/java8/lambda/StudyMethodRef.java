package xin.jerome.java.java8.lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/**
 * 方法引用：若Lambda体中的内容有方法已经实现了，我们可以使用方法引用
 *
 *      语法格式：
 *          1、对象::实例方法名
 *          2、类::静态方法名
 *          3、类::实例方法名
 *
 * @author Jerome Zhu
 * @since 2018.08.23 10:48
 */
public class StudyMethodRef {

    @Test
    public void test1() {
        Consumer<String> con1 = x -> System.out.println(x);
        con1.accept("正常写法！");

        // 函数式接口的抽象方法的参数列表和返回值类型，必须和引用方法的参数列表和返回值类型相同
        Consumer<String> con2 = System.out::println;
        con2.accept("方法引用的写法！");
    }

    @Test
    public void test2() {
        Comparator<Integer> com1 = (x, y) -> Integer.compare(x, y);
        System.out.println(com1.compare(3, 5));

        // 函数式接口的抽象方法的参数列表和返回值类型，必须和引用方法的参数列表和返回值类型相同
        Comparator<Integer> com2 = Integer::compare;
        System.out.println(com2.compare(5, 3));
    }

    @Test
    public void test3() {
        BiPredicate<String, String> bp1 = (x, y) -> x.equals(y);

        // 第一个参数是实例方法的调用者，第二个参数是实例方法的参数
        BiPredicate<String, String> bp2 = String::equals;
    }

}
